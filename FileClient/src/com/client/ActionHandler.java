package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import com.common.Protocol;

public class ActionHandler implements ActionListener, FocusListener, ItemListener{
	private ClientSocket client = null;// 서버와 연결된 oos, ois가 상주하는 핵심 소켓클래스
	
	private LoginView logView = null;
	private AddUserView addView = null;
	private DefaultView defView = null;
	private ChatRoomView chatView = null;
	private CreateChattingView ccView = null;
	private SelectFileView selectView = null;
	
	ActionHandler(){}
	
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
	public void setInstance(SelectFileView selectView) {
		this.selectView = selectView;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
	//로그인뷰 액션
		try {
			if(obj.equals(logView.jbtn_login)||obj.equals(logView.jtf_pw)) {
				Protocol.myID = logView.jtf_id.getText();
				client.send(Protocol.checkLogin
							, logView.jtf_id.getText()
							, logView.jtf_pw.getText());
				
			}else if(obj.equals(logView.jbtn_join)) {
				client.send(Protocol.addUserView);
			}
	//회원가입

			
	//기본화면
			else if(obj.equals(defView.jbtn_chat)) {
				client.send(Protocol.createRoomView, Protocol.myID);
			}
	//유저선택화면
	//		if(obj.equals(ccView.jbtn_create)) {
				
	//		}  CreateChattingView에서 익명클래스로 처리함.
			
	//채팅화면
			else if(obj.equals(chatView.jbtn_send)) {
				client.send(Protocol.sendMessage, chatView.jtf_msg.getText());
			}
	//파일전송화면
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void focusGained(FocusEvent fe) {
		Object obj = fe.getSource();
		
		if(obj == addView.jtf_id ) {
			addView.jtf_id.setText("");
		}
		else if(obj == addView.jtf_pw) {
			addView.jtf_pw.setText("");
		}
		else if(obj == addView.jtf_name) {
			addView.jtf_name.setText("");
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent ie) {
		Object obj = ie.getSource();
		if(ie.getStateChange() == ie.SELECTED) {
			ccView.selected_ID.add(((JCheckBox) ie.getSource()).getText()); //체크박스의 값 들어가야함.
		}
		
		else if(ie.getStateChange() == ie.DESELECTED) {
			ccView.selected_ID.remove(((JCheckBox) ie.getSource()).getText()); //체크박스의 값 들어가야함.
		}
	}
}
