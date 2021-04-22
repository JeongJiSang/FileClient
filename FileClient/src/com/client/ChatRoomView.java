package com.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.common.Protocol;
import com.file.ClientAddress;
import com.file.FileSocket;

public class ChatRoomView extends JFrame{
	ActionHandler action = null;
	ClientSocket client = null;
	String roomName = null;

	JPanel	jp_first = new JPanel();//채팅내용 보여주는 부분.
	JPanel	jp_first_south = new JPanel();//채팅메세지 입력 부분.
	JPanel  jp_second = new JPanel();//채팅방에 있는 유저 보여주기 & 나가기 버튼 부분.

	JTextField jtf_msg = new JTextField(); 
	JTextArea jta_display = new JTextArea();
	JScrollPane jsp_display = new JScrollPane(jta_display,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
	        									, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	JButton jbtn_send  = new JButton("전송");
	JButton jbtn_exit  = new JButton("나가기");
	
	   Font font = new Font("고딕체",Font.BOLD,15);	

	public ChatRoomView(ClientSocket client, String roomName) {
		this.client = client;
		this.roomName = roomName;
		this.setTitle("방 이름 : "+roomName + "  /  내 아이디 : "+Protocol.myID);
		initDisplay();
	}
	
	private void initDisplay() {
		
		//채팅 메세지 보내기.
		jtf_msg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.send(Protocol.sendMessage,roomName
								,Protocol.myID,jtf_msg.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				jtf_msg.setText("");
			}
		});
		jbtn_send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.send(Protocol.sendMessage,roomName
								,Protocol.myID,jtf_msg.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				jtf_msg.setText("");
			}
		});
		
		//채팅방 나가기.
		jbtn_exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("exit!!");
				dispose();
				try {
					client.send(Protocol.closeRoom,roomName, Protocol.myID);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		jta_display.setFont(font);
		jta_display.setEditable(false);
		jta_display.setLineWrap(true);

		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center", jsp_display);
		jp_first.add("South", jp_first_south);

		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		
		jp_second.setLayout(new BorderLayout());
		jp_second.add("South", jbtn_exit);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add("Center", jp_first);
		this.add("East", jp_second);
		this.setBounds(1000, 200, 500, 600);
		//jf.setSize(500, 600);
		this.setVisible(true);
		
	}
}
