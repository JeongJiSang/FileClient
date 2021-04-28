package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.common.Protocol;

public class DefHandler implements ActionListener{
	DefaultView defView = null;
	ClientSocket client = null;
	
	public void setInstance(DefaultView defView,ClientSocket client) {
		this.defView = defView;
		this.client = client;
	}
	
	DefHandler(){
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		try {
			if (obj.equals(defView.jbtn_chat)) {
				client.send(Protocol.createRoomView, Protocol.myID);

			}
			// 로그아웃
			else if (obj.equals(defView.jbtn_logout)) {
				client.send(Protocol.logout, Protocol.myID);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
