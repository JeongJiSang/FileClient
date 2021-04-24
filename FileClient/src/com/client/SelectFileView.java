package com.client;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;

public class SelectFileView extends FileDialog{
	public SelectFileView(JFrame jf) {
		super(jf);
		this.setDirectory("..");
		this.setVisible(true);
		String fileName = this.getFile();
		String filePath = this.getDirectory();
	}
	
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		//SelectFileView s = new SelectFileView(jf);
		
		FileDialog fd = new FileDialog(jf);
		fd.setDirectory("..");
		fd.setVisible(true);
		String fileName = fd.getFile();
		String filePath = fd.getDirectory();
		System.out.println(fileName);
		System.out.println(filePath);
		
	}
}
