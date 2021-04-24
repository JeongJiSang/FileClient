package com.client;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class AddTab extends JFrame {
	JTabbedPane jtap = new JTabbedPane();
	JPanel jp_north = new JPanel();
	JLabel jlb_name = new JLabel(); // 사용자이름
	
	// 중단1
	JPanel jp_online = new JPanel();
	JLabel jlb_online = new JLabel("온라인");
	String online[] = { "아이디" };
	DefaultTableModel dtm_online = new DefaultTableModel(online, 0);
	JTable jtb_online = new JTable(dtm_online);
	JScrollPane jsp_online = new JScrollPane(jtb_online);
	
	// 중단2
	JPanel jp_offline = new JPanel();
	JLabel jlb_offline = new JLabel("오프라인");
	String offline[] = { "아이디" };
	DefaultTableModel dtm_offline = new DefaultTableModel(online, 0);
	JTable jtb_offline = new JTable(dtm_offline);
	JScrollPane jsp_offline = new JScrollPane(jtb_offline);
	// 하단
	JPanel jp_south = new JPanel();
	JButton jbtn_chat = new JButton("방 만들기");
	JButton jbtn_logout = new JButton("로그아웃");
	
	JLabel firstjlb = new JLabel("유저목록", SwingConstants.CENTER);
	JPanel firstjp = new JPanel();
	
	JLabel secondjlb = new JLabel("방목록", SwingConstants.CENTER);
	JPanel secondjp = new JPanel();

	public AddTab() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createTabbedPane();
		setTitle("scheduler");
		setBounds(650, 200, 500, 600);
		setVisible(true);
	}

	private void createTabbedPane() {
		this.add(jtap);

		firstjp.add(firstjlb);
		jtap.addTab("유저목록", new UserList());
		

		secondjp.add(secondjlb);
		jtap.addTab("방목록", new MyPanel());

	}
	//firstPanel - 유저목록
	class UserList extends JPanel{
		public UserList() {
			jp_north.setBounds(0, 20, 500, 40);
			jp_online.setBounds(0, 60, 500, 200);
			jp_offline.setBounds(0, 280, 500, 200);
			jp_south.setBounds(0, 500, 500, 40);
			/////////////////
			jlb_name.setFont(new Font("맑은고딕", Font.BOLD, 15));
			jp_north.add(jlb_name);
			
			jtb_online.addMouseListener(null);
			jp_online.add(jlb_online);
			jp_online.add(jsp_online);
			jp_offline.add(jlb_offline);
			jp_offline.add(jsp_offline);
			jtb_online.addMouseListener(null);
			jtb_offline.addMouseListener(null);

			// 하단
//			jbtn_chat.addActionListener(action);
//			jbtn_logout.addActionListener(action);
			jp_south.add(jbtn_chat);
			jp_south.add(jbtn_logout);

			// 프레임
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JFrame.setDefaultLookAndFeelDecorated(true);
			setLayout(null);
			add(jp_north);
			add(jp_online); // 온라인 테이블 적용
			add(jp_offline); // 오프라인 테이블 적용
			add(jp_south);
			setBounds(650, 200, 500, 600);
			setVisible(true);
		}
	}
	//secondePanel - 방목록
	class roomList extends JPanel{
		
	}
	class MyPanel extends JPanel {
		public MyPanel() {
			this.setLayout(new FlowLayout());
			JTextArea display = new JTextArea(15, 40);
			JTextField input = new JTextField(40);
			this.add(display);
			this.add(input);
		}
	}

	public static void main(String[] ar) {
		new AddTab();
	}
}
