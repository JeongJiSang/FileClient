package com.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.common.Protocol;

public class CreateChattingView extends JFrame{
	ActionHandler action = null;
	ClientSocket client = null;
	//선언부
	List<String> selected_ID = new Vector<>();
	
	JPanel jp_north = new JPanel();
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	
	//GridLayout grid = null;
	JLabel jlb_selectUser = new JLabel("접속중인 유저");
	JCheckBox[] jcb_online = null;
	JButton jbtn_create = new JButton("방 만들기");
	
	//생성자
	
	public CreateChattingView(ClientSocket client,ActionHandler action, List<String> chatMember) {
		this.client = client;
		this.action = action;
		jp_center.setLayout(new GridLayout(chatMember.size(), 1, 2, 2));
		jcb_online = new JCheckBox[chatMember.size()];
		for(int i=0; i<jcb_online.length;i++) {
			
			jcb_online[i] = new JCheckBox(chatMember.get(i));
			jcb_online[i].addItemListener(action);
			jp_center.add(jcb_online[i]);
		}
		initDisplay();
	}
	//화면처리부
	private void initDisplay() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		//////상단
		jlb_selectUser.setFont(new Font("고딕체", Font.BOLD, 15));
		jp_north.add(jlb_selectUser);
		jp_north.setBackground(Color.WHITE);
		add("North",jp_north);
		
		///////중단
		jp_center.setBackground(Color.WHITE);
		add("Center",jp_center);
		
		///////하단
		//jbtn_create.addActionListener(action);
		jp_south.add(jbtn_create);
		add("South",jp_south);
		
		//////채팅방 생성 버튼!!! 왜 익명클래스로 처리했는데 액션핸들러에 주석 추가함.
		jbtn_create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String roomName = JOptionPane.showInputDialog("방 이름을 설정해주세요.");
				try {
					client.send(Protocol.createRoom,roomName
							,Protocol.myID,selected_ID.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				dispose();
			}
		});
		
		//////
		setTitle("초대 유저 선택");
		setBounds(1150, 200, 300, 400);
		setVisible(true);
	}
}
