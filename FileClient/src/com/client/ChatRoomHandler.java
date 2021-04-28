package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import com.common.Protocol;

public class ChatRoomHandler implements ActionListener {
	private ClientSocket client = null;
	private ChatRoomView chatView = null;
	
	public void setInstance(ChatRoomView chatView ,ClientSocket client) {
		this.chatView = chatView;
		this.client =client;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		try {
			if(obj == chatView.jtf_msg) {
				try {
					client.send(Protocol.sendMessage,chatView.roomName
								,Protocol.myID,chatView.jtf_msg.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				chatView.jtf_msg.setText("");
			}
		
			else if(obj == chatView.jbtn_send) {
				try {
					System.out.println("전송!!");
					System.out.println("ClientSocket : "+client);
					client.send(Protocol.sendMessage,chatView.roomName
								,Protocol.myID,chatView.jtf_msg.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				chatView.jtf_msg.setText("");
			}
			else if(obj == chatView.jbtn_exit) {
				System.out.println("exit!!");
				chatView.dispose();
				try {
					client.send(Protocol.closeRoom,chatView.roomName, Protocol.myID);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(obj == chatView.jbtn_file) {
				chatView.fd.setDirectory("..");
				chatView.fd.setVisible(true);
			    String fileName = chatView.fd.getFile();
			    String filePath = chatView.fd.getDirectory();
			    System.out.println(fileName);
			    System.out.println(filePath);
			    System.out.println(filePath+fileName);
			    try {
			    	File file = new File(filePath+fileName);
			    	client.send(chatView.roomName, file);
					client.send(Protocol.sendFile,chatView.roomName, filePath, fileName, Protocol.myID);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
