package com.client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.common.Protocol;

//서버로부터 수신받은 오브젝트를 처리하는 클래스
public class ClientThread extends Thread{
	ClientSocket client = null;// 서버와 연결된 oos, ois가 상주하는 핵심 소켓클래스
	
	AddUserHandler addHandler = null;
	LoginHandler logHandler = null;
	DefHandler defHandler = null;
	CreateChattingHandler ccHandler = null;
	ChatRoomHandler crHandler = null;
	
	LoginView logView = null;
	AddUserView addView = null;
	DefaultView defView = null;
	CreateChattingView ccView = null;
	ChatRoomView chatView = null;
	
	/************************
	 * Key : roomName
	 * Value : ChatRoomView
	 ************************/
	Map<String, ChatRoomView> chatRoomList= null;
	
	public ClientThread(ClientSocket client) {
		this.client = client;
		logView = new LoginView(client);// 최초 로그인 뷰 실행
		chatRoomList = new Hashtable<String, ChatRoomView>();
	}
	/**
	 * String으로 들어온 list 변환 메소드
	 */
	private List<String> decompose(String result){
		List<String> list = new Vector<>();
		String[] values = result.replaceAll("\\p{Punct}", "").split(" ");
		for(String str:values) {
			list.add(str);
		}
		return list;
	}
	
	public void showRoom() {
		while(defView.dtm_room.getRowCount()>0) {
			defView.dtm_room.removeRow(0);
		}
		for(String roomName:chatRoomList.keySet()) {
			Vector<Object> oneRow = new Vector<Object>();
			oneRow.add(roomName);
			defView.dtm_room.addRow(oneRow);
		}
	}
	
	public void run(){
		boolean isStop = false;
		while(!isStop) {
			try {
				String msg = client.ois.readObject().toString();
				StringTokenizer st = new StringTokenizer(msg, "#");
				switch(st.nextToken()) {
				case Protocol.checkLogin:{//100#
					String result = st.nextToken();
					if("difid".equals(result)) {
						JOptionPane.showMessageDialog(logView, "아이디가 존재하지 않습니다");
					}
					else if("difpw".equals(result)) {
						JOptionPane.showMessageDialog(logView, "비밀번호가 일치하지 않습니다");
					}
					else if("overlap".equals(result)) {
						JOptionPane.showMessageDialog(logView, "이미 로그인된 아이디입니다.");
					}
					else if(Protocol.myID.equals(result)) {
						//온라인 리스트 벡터 가져오기
						defView = new DefaultView(client);
						logView.dispose();
					}
				}break;
				case Protocol.addUserView:{//111#
					if(addView!=null) {
						addView.toFront();
					}else {
						addView = new AddUserView(client);
					}
					
				}break;
				case Protocol.addUser:{//110#결과값
					String result = st.nextToken();
					if("success".equals(result)) {
						JOptionPane.showMessageDialog(addView, addView.jtf_id.getText()+"님 가입을 환영합니다.");
						addView.dispose();
					}else if("fail".equals(result)) {
						JOptionPane.showMessageDialog(addView, "이미 등록된 아이디 입니다.");
					}
				}break;
				case Protocol.showUser:{//120#
					List<String> onlineUser = decompose(st.nextToken());
					List<String> offlineUser = decompose(st.nextToken());
					//온라인 유저 데이터 입력
					while(defView.dtm_online.getRowCount()>0) {
						defView.dtm_online.removeRow(0);
					}
					for(Object obj:onlineUser) {
						Vector<Object> oneRow = new Vector<Object>();
						oneRow.add(obj);
						defView.dtm_online.addRow(oneRow);
					}
					//오프라인 유저 데이터 입력
					while(defView.dtm_offline.getRowCount()>0) {
						defView.dtm_offline.removeRow(0);
					}
					for(Object obj:offlineUser) {
						Vector<Object> oneRow = new Vector<Object>();
						oneRow.add(obj);
						defView.dtm_offline.addRow(oneRow);
					}
				}break;
				case Protocol.logout:{//130#logoutID#roomName
					String logoutID = st.nextToken();
					String roomName = st.nextToken();
					for(String room:chatRoomList.keySet()) {
						if(roomName.equals(room)) {
							chatView = chatRoomList.get(room);
							chatView.sd_display.insertString
							(chatView.sd_display.getLength(), logoutID+" 님이 로그아웃 하셨습니다."+"\n", null);
						}
					}
				}break;
				case Protocol.createRoomView:{//201#chatMember(나를 제외한)#serverRooms
					List<String> chatMember = decompose(st.nextToken());
					List<String> serverRooms = decompose(st.nextToken());
					if(defView.dtm_online.getRowCount()>=2) {
						ccView = new CreateChattingView(client,chatMember, serverRooms);
					}else {
						JOptionPane.showMessageDialog(defView, "현재 접속중인 유저가 한 명 뿐입니다.", "메시지", JOptionPane.WARNING_MESSAGE);
					}
				}break;
				case Protocol.createRoom:{//200#roomName#chatMember
					String roomName = st.nextToken();
					chatView = new ChatRoomView(client,roomName);
					//만들어진 채팅방을 Map으로 관리. key: roomName, value: chatView.
					chatRoomList.put(roomName, chatView);
					//로그아웃, 나가기에 remove추가할 것
					showRoom();
				}break;
				/*
				case Protocol.enterRoom:{//203#id#roomName#result
					String id = st.nextToken();
					String roomName = st.nextToken();
					String result = st.nextToken();
					if(result.equals("enter")) {
						if(id.equals(Protocol.myID)) { //입장하는 본인일 경우
							chatView = new ChatRoomView(client,roomName); //채팅룸뷰를 켜줌
							chatRoomList.put(roomName, chatView); //입장한 클라이언트측에 방이름과 채팅룸의 주소번지 저장
						}else { //본인이 아닌경우(원래 방에 있던 사람일 경우)
							
							//고치기!!!!
							chatView = chatRoomList.get(roomName); 
							//sendMessage를 거치지않은 초대받은 유저라면, chatRoom에 올라가있는 chatView가 없기 때문에 에러 발생
							
							chatView.sd_display.insertString(chatView.sd_display.getLength(), id+" 님이 중간입장 하셨습니다."+"\n", null);
						}
					} else if(result.equals("overlap")) {
						JOptionPane.showMessageDialog(defView, "이미 입장하신 채팅방입니다.");
					}
				}break;*/
				case Protocol.closeRoom:{//210#roomName#id
					String roomName = st.nextToken();
					String id = st.nextToken();
						for(String room : chatRoomList.keySet()) {
							if(room.equals(roomName)) {
								chatView = chatRoomList.get(roomName);
								chatView.sd_display.insertString(chatView.sd_display.getLength()
										,id+" 님이 "+roomName+"에서 퇴장하셨습니다."+"\n"
												,null);
							}
						}
					showRoom();
				}break;
				case Protocol.inviteUser:{//204#roomName#chatMember(나를 제외한 온라인 유저들)
					String roomName = st.nextToken();
					List<String> chatMember = decompose(st.nextToken());
					ccView = new CreateChattingView(client,roomName,chatMember);
				}break;
				case Protocol.inviteUserEnter:{//205#roomName#chatMember(초대된 유저들)
					String roomName = st.nextToken();
					List<String> chatMember = decompose(st.nextToken());
					
					boolean success = true;
					for(String room:chatRoomList.keySet()) {
						if(room.equals(roomName)) {
							chatView = chatRoomList.get(roomName); //주소번지 들어감
							chatView.sd_display.insertString(
									chatView.sd_display.getLength()
									,chatMember+" 님이 초대되었습니다. "
									+"\n",null);
							success = false;
						}
					}
					if(success) { //폼이 안켜져있는 경우(초대된 애들)
						chatView = new ChatRoomView(client, roomName);
						chatRoomList.put(roomName, chatView);
						chatView.sd_display.insertString(
								chatView.sd_display.getLength()
								,chatMember+" 님이 초대되었습니다. "
								+"\n",null);
					}
				}break;
				case Protocol.sendMessage:{//300#roomName#id#msg
					String roomName = st.nextToken();
					String chat_id = st.nextToken();
					String chat_msg = st.nextToken();
					
					boolean success = true;
					for(String room:chatRoomList.keySet()) {
						if(room.equals(roomName)) {
							chatView = chatRoomList.get(roomName); //주소번지 들어감
							chatView.sd_display.insertString(
									chatView.sd_display.getLength()
									,"<"+chat_id+">"+" : "
									+chat_msg+"\n"
									,null);
							success = false;
						}
					}
					if(success) { //폼이 안켜져있는 경우(초대된 애들)
						chatView = new ChatRoomView(client, roomName);
						chatRoomList.put(roomName, chatView);
						chatView.sd_display.insertString(
								chatView.sd_display.getLength()
								,"<"+chat_id+">"+" : "
								+chat_msg+"\n"
								,null);
						showRoom();
					}
					
				}break;
				case Protocol.sendEmoticon:{//310#
					
					
				}break;
				case Protocol.sendFile:{//320#roomName#id#fileName
					String roomName = st.nextToken();
					String chat_id = st.nextToken();
					String fileName = st.nextToken();
					
					boolean success = true;
					for(String room : chatRoomList.keySet()) {
						if(room.equals(roomName)) {
							chatView = chatRoomList.get(roomName);
							chatView.sd_display.insertString(
									chatView.sd_display.getLength()
									,"<"+chat_id+">"+" 님이"+"==========="+"\n"
									+"["+fileName+"]"+"을/를 전송하였습니다."+"\n"
									,null);
							//fileName으로된 JButton 생성.
							//JButton jbtn_file = new JButton(fileName);
							
							//fileName으로된 JLbel 생성.
							JLabel jlb_file = new JLabel(fileName);
							jlb_file.setForeground(Color.BLUE.darker());
							jlb_file.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							
							chatView.jp_file.add(jlb_file);
							chatView.jp_file.revalidate();
							
							jlb_file.addMouseListener(new MouseAdapter() {
							    @Override
							    public void mouseClicked(MouseEvent e) {
									try {
										client.receive(roomName, fileName);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
							    }
					            @Override
					            public void mouseExited(MouseEvent e) {
					            	jlb_file.setText(fileName);
					            }
					            @Override
					            public void mouseEntered(MouseEvent e) {
					            	jlb_file.setText("<html><a href=''>" + fileName + "</a></html>");
					            }
							});///////////////// addMouseListener //////
							
							success = false;
						}
					}
					if(success) { //폼이 안켜져있는 경우
						chatView = new ChatRoomView(client,roomName);
						chatRoomList.put(roomName, chatView);
						chatView.sd_display.insertString(
								chatView.sd_display.getLength()
								,"<"+chat_id+">"+" 님이"+"==========="+"\n"
								+"["+fileName+"]"+"을/를 전송하였습니다."+"\n"
								,null);
						
						//fileName으로된 JLbel 생성.
						JLabel jlb_file = new JLabel(fileName);
						jlb_file.setForeground(Color.BLUE.darker());
						jlb_file.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						
						//JLabel로 나타냄.
						chatView.jp_file.add(jlb_file);
						chatView.jp_file.revalidate();
						
						jlb_file.addMouseListener(new MouseAdapter() {
						    @Override
						    public void mouseClicked(MouseEvent e) {
								try {
									client.receive(roomName, fileName);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
						    }
				            @Override
				            public void mouseExited(MouseEvent e) {
				            	jlb_file.setText(fileName);
				            }
				            @Override
				            public void mouseEntered(MouseEvent e) {
				            	jlb_file.setText("<html><a href=''>" + fileName + "</a></html>");
				            }
						});///////////////// addMouseListener //////
					}
				}break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.destroy();
			}
		}
	}
}
