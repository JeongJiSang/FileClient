package com.client;

import java.io.File;
import java.io.IOException;

public class ClientMain {

	public static void main(String[] args) {
		try {
			ClientAddress chatAddress = new ClientAddress("localhost", 9100);
			ClientAddress fileAddress = new ClientAddress("localhost", 9101);
			ClientSocket client = new ClientSocket(chatAddress, fileAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} 