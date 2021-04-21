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
	//ActionHandler action = null;
	ClientSocket client = null;
	DefaultView defView = null;
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
	
	public CreateChattingView(ClientSocket client, DefaultView defView) {
		this.client = client;
		this.defView = defView;
	}
	
	//체크박스 생성 메소드
	void checkbox() {
		jp_center = new JPanel(new GridLayout(defView.dtm_online.getRowCount(),1,2,2)); //접속중 유저만큼 그리드레이아웃 만들기
		onlines = new String[defView.dtm_online.getRowCount()]; 	  //dtm값 넣을 배열 크기 초기화
		jcb_online = new JCheckBox[defView.dtm_online.getRowCount()]; //체크 박스 크기 초기화
		
		for(int i=0; i<defView.dtm_online.getRowCount(); i++) {    
			if(!Protocol.myID.equals(defView.dtm_online.getValueAt(i, 0))) {//equals써보자
				onlines[i]=defView.dtm_online.getValueAt(i, 0).toString(); //dtm값을 배열에 넣기
				jcb_online[i] = new JCheckBox(onlines[i]); //배열의 값을 담은 체크박스 생성
				jp_center.add(jcb_online[i]); //체크박스 패널에 추가
				jcb_online[i].addItemListener(this); //이벤트 처리
			}
		}
		
	}
	
	//화면처리부
	public void initDisplay() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		jp_center.setBackground(Color.WHITE);
		//채팅방 생성 버튼!!!
		jbtn_create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
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
