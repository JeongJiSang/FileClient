package com.client;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.common.Protocol;

//서버로부터 수신받은 오브젝트를 처리하는 클래스
public class ClientThread extends Thread{
	ClientSocket client = null;// 서버와 연결된 oos, ois가 상주하는 핵심 소켓클래스
	ActionHandler action = null;
	
	LoginView logView = null;
	AddUserView addView = null;
	DefaultView defView = null;
	CreateChattingView ccView = null;
	ChatRoomView chatView = null;
	SelectFileView selectView = null;

	Map<String, ChatRoomView> chatRoomList= null;
	
	public ClientThread(ClientSocket client) {
		this.client = client;
		action = new ActionHandler();// 액션리스너클래스 실행
		logView = new LoginView(action);// 최초 로그인 뷰 실행
		action.setInstance(logView, client); // 액션리스너클래스에 로그인뷰 주소번지 인입
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
						defView = new DefaultView(action);
						action.setInstance(defView); //메인화면 띄움
						logView.dispose();
					}
					defView = new DefaultView(action);
					action.setInstance(defView);
					
				}break;
				case Protocol.addUserView:{//111#
					if(addView!=null) {
						addView.toFront();
					}else {
						addView = new AddUserView(action);
						action.setInstance(addView);
					}
					
				}break;
				case Protocol.addUser:{//110#
					String result = st.nextToken();
					if("성공".equals(result)) {
						JOptionPane.showMessageDialog(addView, "님 가입을 환영합니다.");
						addView.dispose();
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
				case Protocol.createRoom:{//200#
					String roomName = st.nextToken();
					chatView = new ChatRoomView(client, roomName);
					chatRoomList.put(roomName, chatView);
					
				}break;
				case Protocol.closeRoom:{//210#
					
					
				}break;
				case Protocol.sendMessage:{//300#
					String chat_id = st.nextToken();
					String roomName = st.nextToken();
					String chat_msg = st.nextToken();
					
					boolean success = true;
					for(String room:chatRoomList.keySet()) {
						if(room.equals(roomName)) {
							chatView = chatRoomList.get(roomName); //주소번지 들어감
							chatView.jta_display.append(chat_id+" : "+chat_msg+"\n");
							success = false;
						}
					}
					if(success) {
						chatView = new ChatRoomView(client, roomName);
						chatRoomList.put(roomName, chatView);
						chatView.jta_display.append(chat_id+" : "+chat_msg+"\n");
					}
					
				}break;
				case Protocol.sendEmoticon:{//310#
					
					
				}break;
				case Protocol.sendFile:{//320#
					
					
				}break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.destroy();
			}
		}
	}
}
