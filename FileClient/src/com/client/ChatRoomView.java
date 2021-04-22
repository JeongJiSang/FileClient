package com.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.common.Protocol;

public class ChatRoomView extends JFrame{
	ClientSocket client = null;
	String roomName = null;

	JPanel	jp_first = new JPanel();//채팅내용 보여주는 부분.
	JPanel	jp_first_south = new JPanel();//채팅메세지 입력 부분.
	JPanel  jp_second = new JPanel();//채팅방에 있는 유저 및 나가기 버튼 부분.

	JTextField jtf_msg = new JTextField(); 
	JTextArea jta_display = new JTextArea();
	JScrollPane jsp_display = new JScrollPane(jta_display);

	JButton jbtn_send  = new JButton("전송");
	JButton jbtn_exit  = new JButton("나가기");

	public ChatRoomView(ClientSocket client, String roomName) {
		this.client = client;
		this.roomName = roomName;
		this.setTitle("방 이름 : "+roomName + "  /  내 아이디 : "+Protocol.myID);
		initDisplay();
	}
	
	public ChatRoomView() {
		initDisplay();
	}

	private void initDisplay() {
		
		//채팅 메세지 보내기.
		jtf_msg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("message enter!");
				String chtMsg = jtf_msg.getText();
			}
		});
		jbtn_send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("button clicked!");
				
			}
		});
		
		//채팅방 나가기.
		jbtn_exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("exit!!");
				try {
					client.send(roomName, Protocol.myID);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		jta_display.setFont(new Font("고딕체",Font.BOLD,20));

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
	
	public static void main(String[] args) {
		new ChatRoomView();
	}
}
