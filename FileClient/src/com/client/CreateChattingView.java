package com.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.common.Protocol;

public class CreateChattingView extends JFrame implements ItemListener{
	ActionHandler action = null;
	ClientSocket client = null;
	//선언부
	String onlines[] = null;
	List<String> selected_ID = new Vector<>();
	
	JPanel jp_north = new JPanel();
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	JLabel jlb_selectUser = new JLabel("접속중인 유저");
	JCheckBox[] jcb_online = null;
	JButton jbtn_create = new JButton("방 만들기");
	
	//생성자
	
	public CreateChattingView(ActionHandler action) {
		this.action = action;
		initDisplay();
	}
	public CreateChattingView(ClientSocket client) {
		this.client = client;
		initDisplay();
	}
	
	//화면처리부
	private void initDisplay() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		jp_center.setBackground(Color.WHITE);
		jbtn_create.addActionListener(action);
		jlb_selectUser.setFont(new Font("고딕체", Font.BOLD, 15));
		jp_north.add(jlb_selectUser);
		jp_south.add(jbtn_create);
		jp_north.setBackground(Color.WHITE);
		add("North",jp_north);
		add("Center",jp_center);
		add("South",jp_south);
		setTitle("초대 유저 선택");
		setBounds(1150, 200, 300, 400);
		setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
