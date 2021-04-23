package com.client;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
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
	ActionHandler action = null;
	ClientSocket client = null;
	String roomName = null;

	JPanel	jp_first = new JPanel();//채팅내용 보여주는 부분.
	JPanel	jp_first_south = new JPanel();//채팅메세지 입력 부분.
	JPanel  jp_second = new JPanel();//채팅방에 있는 유저 보여주기.
	JPanel  jp_second_south = new JPanel();

	JTextField jtf_msg = new JTextField(); 
	JTextArea jta_display = new JTextArea();
	JTextArea jta_user_display = new JTextArea();//채팅방 유저 아이디 부분.
	JScrollPane jsp_display = new JScrollPane(jta_display,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
	        									, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JScrollPane jsp_user_display = new JScrollPane(jta_user_display,
												JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
												,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	JButton jbtn_send  = new JButton("전송");
	JButton jbtn_exit  = new JButton("나가기");
	JButton jbtn_file  = new JButton("파일전송");
	
	FileDialog fd = new FileDialog(this);
	
	Font font = new Font("고딕체",Font.BOLD,15);	

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
		
		//파일전송.
		jbtn_file.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("file!!");
			    fd.setDirectory("..");
			    fd.setVisible(true);
			    String fileName = fd.getFile();
			    String filePath = fd.getDirectory();
			    System.out.println(fileName);
			    System.out.println(filePath);
			    try {
					client.send(Protocol.sendFile,roomName, filePath, fileName, Protocol.myID);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		//show 채팅내용
		jta_display.setFont(font);
		jta_display.setEditable(false);
		jta_display.setLineWrap(true);
		
		//show 채팅방유저 
		jta_user_display.setFont(font);
		jta_user_display.setEditable(false);

		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center", jsp_display);
		jp_first.add("South", jp_first_south);

		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		
		jp_second.setLayout(new BorderLayout());
		jp_second.add("Center", jsp_user_display);
		jp_second.add("South", jp_second_south);
		
		jp_second_south.setLayout(new GridLayout(0,1,2,2));
		jp_second_south.add(jbtn_file);
		jp_second_south.add(jbtn_exit);
		
		
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
