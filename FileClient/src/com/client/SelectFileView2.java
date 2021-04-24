package com.client;


import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Panel;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class SelectFileView2 extends JFrame{
	
	
	public static void main(String[] args) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showDialog(new JFrame(), null);

		File dir = jfc.getSelectedFile();
		String a= dir.getPath();
		System.out.println(a);
		//jt_save_path.setText(dir!=null?dir.getPath():"");
	}
}
