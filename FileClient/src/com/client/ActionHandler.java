package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener{
	LoginView logView = null;
	
	ActionHandler(LoginView logView){
		this.logView = logView;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
			
	}

}
