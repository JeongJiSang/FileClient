package com.client;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectUserView extends JFrame{
	ActionHandler action = null;
	
	JPanel jp_north = new JPanel();
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	JLabel jlb_selectUser = new JLabel("접속중인 유저");
	JCheckBox[] jcb_online = null;
	JCheckBox jcb_apple = null;
	JButton jbtn_create = new JButton("방 만들기");
	
	public SelectUserView(ActionHandler action) {
		this.action=action;
	}
	
	private void initDisplay() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		jp_center.setBackground(Color.WHITE);
		jbtn_create.addActionListener(action);
		jlb_selectUser.setFont(new Font("고딕체", Font.BOLD, 15));
		jp_north.add(jlb_selectUser);
		jp_south.add(jbtn_create);
		jp_north.setBackground(Color.WHITE);
		this.add("North",jp_north);
		this.add("Center",jp_center);
		this.add("South",jp_south);
		this.setTitle("초대 유저 선택");
		this.setBounds(1000, 200, 300, 400);
		this.setVisible(true);
	}
}
