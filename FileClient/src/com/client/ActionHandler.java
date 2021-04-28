package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import com.common.Protocol;

public class ActionHandler implements ActionListener, FocusListener, ItemListener {
	private ClientSocket client = null;// 서버와 연결된 oos, ois가 상주하는 핵심 소켓클래스

	private LoginView logView = null;
	private AddUserView addView = null;
	private DefaultView defView = null;
	private ChatRoomView chatView = null;
	private CreateChattingView ccView = null;

	ActionHandler() {
	}

	public void setInstance(LoginView logView, ClientSocket client) {
		this.logView = logView;
		this.client = client;
	}

	public void setInstance(AddUserView addView) {
		this.addView = addView;
	}

	public void setInstance(DefaultView defView) {
		this.defView = defView;
	}

	public void setInstance(ChatRoomView chatView) {
		this.chatView = chatView;
	}

	public void setInstance(CreateChattingView ccView) {
		this.ccView = ccView;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		// 로그인뷰 액션
		try {
			if (obj.equals(logView.jbtn_login) || obj.equals(logView.jtf_pw)) {
				Protocol.myID = logView.jtf_id.getText();
				client.send(Protocol.checkLogin, logView.jtf_id.getText(), logView.jtf_pw.getText());

			} else if (obj.equals(logView.jbtn_join)) {
				client.send(Protocol.addUserView);
			}
			
			// 로그인창에서 아무것도 입력안하고 버튼 눌렀을때 먹통현상 해결중 -상철님
			/*
			if (obj == logView.jbtn_login || obj == logView.jtf_pw) {
				String id = logView.jtf_id.getText();
				String pw = logView.jtf_pw.getText();
				System.out.println(id+", "+pw);
				boolean isOK = false;
				if(!isOK) {
					System.out.println("null 이다");
					JOptionPane.showMessageDialog(logView, "내용을 입력해주세요.");
					isOK = true;
				}
				else if(isOK){
					System.out.println("지나감??");
					Protocol.myID = logView.jtf_id.getText();
					client.send(Protocol.checkLogin, logView.jtf_id.getText(), logView.jtf_pw.getText());
				}

			} else if (obj.equals(logView.jbtn_join)) {
				client.send(Protocol.addUserView);

			}*/

		// 기본화면
			else if (obj.equals(defView.jbtn_chat)) {
				client.send(Protocol.createRoomView, Protocol.myID);

			}
		// 로그아웃
			else if (obj.equals(defView.jbtn_logout)) {
				client.send(Protocol.logout, Protocol.myID);
			}
			
	//유저선택화면
			/* ccView에서 익명으로 처리한 이유: 중간 입장입장 경우에는 유저선택화면을 안거치기때문에 ccView를 인스턴스와 받을일이 없음.
			 * 따라서 acionHandler에서 중간에 null이 뜨기때문에 에러뜸.
			else if(obj.equals(ccView.jbtn_create)) {
				String roomName = JOptionPane.showInputDialog("방 이름을 설정해주세요.");
				try {
					client.send(Protocol.createRoom, roomName, Protocol.myID, ccView.selected_ID.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				ccView.dispose();
			}*/
	//채팅화면
			/*
			else if(obj.equals(chatView.jbtn_send)) {
				client.send(Protocol.sendMessage, chatView.jtf_msg.getText());
			}*/
	//중간입장
			else if(obj.equals(defView.jbtn_enter)) {
				//디티엠 선택한 방이름 getText하기 ->테이블 안됨, 방법있나..?
				String roomName = getroomName();
				client.send(Protocol.enterRoom,Protocol.myID,roomName);
			}
			
	//파일전송화면
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void focusGained(FocusEvent fe) {
		Object obj = fe.getSource();

		if (obj == addView.jtf_id) {
			addView.jtf_id.setText("");
		} else if (obj == addView.jtf_pw) {
			addView.jtf_pw.setText("");
		} else if (obj == addView.jtf_name) {
			addView.jtf_name.setText("");
		}

	}
	
	public String getroomName() { //위치 여기면 안됨, 아니면 선택했을때 getText되는 방법 찾기
		int row = defView.jtb_room.getSelectedRow();
		String roomName = defView.jtb_room.getValueAt(row, 0).toString();
		return roomName;
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		Object obj = ie.getSource();
		
		
		//for(JCheckBox box : ccView.jcb_online) {
		//	if(!box.isSelected()) {
		//		box.setEnabled(false);
		//	}
		//}
		
		if (ie.getStateChange() == ie.SELECTED) {
			ccView.selected_ID.add(((JCheckBox) ie.getSource()).getText()); // 체크박스의 값 들어가야함.
		}

		else if (ie.getStateChange() == ie.DESELECTED) {
			ccView.selected_ID.remove(((JCheckBox) ie.getSource()).getText()); // 체크박스의 값 들어가야함.
		}
	}
}
