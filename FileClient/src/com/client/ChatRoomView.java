package com.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.common.Protocol;

public class ChatRoomView extends JFrame{
	ActionHandler action = null;

	JPanel	jp_first = new JPanel();
	JPanel	jp_first_south = new JPanel();

	JTextField jtf_msg = new JTextField(); 
	JTextArea jta_display = new JTextArea();
	JScrollPane jsp_display = new JScrollPane(jta_display);

	JButton jbtn_send  = new JButton("전송");

	public ChatRoomView(ClientSocket client, String roomName) {
		this.setTitle("방 이름 : "+roomName + "  /  내 아이디 : "+Protocol.myID);
		initDisplay();
	}
	private void initDisplay() {

		jtf_msg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		jbtn_send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		jta_display.setFont(new Font("고딕체",Font.BOLD,20));

		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center", jsp_display);
		jp_first.add("South", jp_first_south);

		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);

		this.add(jp_first);
		this.setBounds(1000, 200, 500, 600);
		//jf.setSize(500, 600);
		this.setVisible(true);
	}
}
