package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.common.Protocol;

public class AddUserView extends JDialog{
	ActionHandler action = null;
	ClientSocket client = null;
	
	JLabel 			jlb_id = new JLabel("아이디");
	JLabel 			jlb_pw = new JLabel("비밀번호");
	JLabel 			jlb_name = new JLabel("이름");
	JTextField 		jtf_id = new JTextField("");
	JTextField 		jtf_pw = new JTextField("");
	JTextField 		jtf_name = new JTextField("");
	JButton 		jbtn_join = new JButton("가입신청");

	public AddUserView(ClientSocket client/*ActionHandler action*/) {
		//this.action=action;
		this.client = client;
		initDisplay();
	}
	
	private void initDisplay() {

		//라벨 추가.
		this.add(jlb_id);
		this.add(jlb_pw);
		this.add(jlb_name);

		//라벨 위치 세팅.
		jlb_id.setBounds(55, 50, 80, 40);
		jlb_pw.setBounds(55, 100, 80, 40);
		jlb_name.setBounds(55, 150, 80, 40);

		//텍스트 필드 추가.
		this.add(jtf_id);
		this.add(jtf_pw);
		this.add(jtf_name);

		//텍스트 필드 위치 세팅.
		jtf_id.setBounds(120, 50, 180, 40);
		jtf_pw.setBounds(120, 100, 180, 40);
		jtf_name.setBounds(120, 150, 180, 40);

		//버튼추가.
		this.add(jbtn_join);
		jbtn_join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String new_id = jtf_id.getText();
				String new_pw = jtf_pw.getText();
				String new_name = jtf_name.getText();
				try {
					client.send(Protocol.addUser, new_id, new_pw, new_name);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		//버튼 위치 세팅.
		jbtn_join.setBounds(160, 270, 100, 40);

		//dialog 세팅.
		//this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(400, 400);
		this.setVisible(true);
	}



}
