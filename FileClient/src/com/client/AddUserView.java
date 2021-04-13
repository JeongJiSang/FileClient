package com.client;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddUserView extends JDialog{
	JLabel 			jlb_id = new JLabel("아이디");
	JLabel 			jlb_pw = new JLabel("비밀번호");
	JLabel 			jlb_name = new JLabel("이름");
	JTextField 		jtf_id = new JTextField("아이디를 설정해주세요.");
	JTextField 		jtf_pw = new JTextField("비밀번호를 설정해주세요.");
	JTextField 		jtf_name = new JTextField("이름을 입력해주세요.");
	JButton 		jbtn_join = new JButton("가입신청");
	
	public void initDisplay() {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//이벤트
		jbtn_join.addActionListener(null);
		jtf_id.addFocusListener(null);
		jtf_pw.addFocusListener(null);
		jtf_name.addFocusListener(null);
		
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
		
		//버튼 위치 세팅.
		jbtn_join.setBounds(160, 270, 100, 40);
		
		//dialog 세팅.
		this.setLayout(null);
		this.setSize(400, 400);
		this.setVisible(true);
	}
	
	
	
}
