package com.client;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectFileView extends Frame{
	
	JPanel jp_north = new JPanel();
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	JLabel jlb_selectUser = new JLabel("접속중인 유저");
	JCheckBox[] jcb_online = null;
	JCheckBox jcb_apple = null;
	JButton jbtn_create = new JButton("방 만들기");
	
	public SelectFileView() {}
	
	
}
