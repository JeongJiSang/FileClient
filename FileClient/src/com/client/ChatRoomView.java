package com.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.common.Protocol;

public class ChatRoomView extends JFrame{
	ActionHandler action = null;
	ClientSocket client = null;
	String roomName = null;
	FileDialog fd = new FileDialog(this);
	
	JPanel	jp_first = new JPanel();//채팅내용 보여주는 부분.
	JPanel	jp_first_south = new JPanel();//채팅메세지 입력 부분.
	JPanel  jp_second = new JPanel();
	JPanel  jp_second_south = new JPanel();
	
	JPanel  jp_file = new JPanel();
	JPanel  jp_file_title = new JPanel();
	JLabel	jlb_file_title = new JLabel("File Store");
	
	StyledDocument sd_display = 
			new DefaultStyledDocument(
					new StyleContext());
	JTextField jtf_msg = new JTextField(); 
	JTextPane jtp_display = new JTextPane(sd_display);
	
	JScrollPane jsp_display = new JScrollPane(jtp_display,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
	        									, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JScrollPane jsp_file_display = new JScrollPane(jp_file,
												JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
												,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	JButton jbtn_send  = new JButton("전송");
	JButton jbtn_exit  = new JButton("나가기");
	JButton jbtn_file  = new JButton("파일전송");
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
					System.out.println("전송엔터 눌렸다 ");
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
					System.out.println("전송버튼 눌렸다 ");
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
				fd.setDirectory("..");
			    fd.setVisible(true);
			    String fileName = fd.getFile();
			    String filePath = fd.getDirectory();
			    System.out.println(fileName);
			    System.out.println(filePath);
			    System.out.println(filePath+fileName);
			    try {
			    	File file = new File(filePath+fileName);
			    	client.send(roomName, file);
					client.send(Protocol.sendFile,roomName, filePath, fileName, Protocol.myID);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		//show 채팅내용
		//jtp_display에 LineWrap필요....!!! 방법을 모름. 구글링 해도 못찾겠음 Tlqkf.
		jtp_display.setFont(font);
		jtp_display.setEditable(false);
		jsp_display.setViewportView(jtp_display);//작동 되는지 모르겠음
		
		//채팅창.
		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center", jsp_display);
		jp_first.add("South", jp_first_south);
		
		//채팅msg입력 및 전송 버튼 부분.
		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center",jtf_msg);
		jp_first_south.add("East",jbtn_send);
		
		//전송된 파일 보여주는 부분.
		jp_second.setLayout(new BorderLayout());
		jp_second.add("Center", jp_file);
		jp_second.add("North", jp_file_title);
		jp_file.setLayout(new BoxLayout(jp_file, BoxLayout.Y_AXIS));
		
		//파일 panel title.(File Store)
		jp_file_title.add("Center",jlb_file_title);
		jp_file_title.setBackground(Color.DARK_GRAY);
		jlb_file_title.setForeground(Color.WHITE);
		jlb_file_title.setFont(font);
		
		//파일전송, 나가기 버튼 부분.
		jp_second.add("South", jp_second_south);
		jp_second_south.setLayout(new GridLayout(0,1,2,2));
		jp_second_south.add(jbtn_file);
		jp_second_south.add(jbtn_exit);
		
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add("Center", jp_first);
		this.add("East", jp_second);
		this.setBounds(650, 200, 500, 600);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ChatRoomView();
	}
}
