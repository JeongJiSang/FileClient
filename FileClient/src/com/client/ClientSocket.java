package com.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Stack;

import com.common.Protocol;

//전송관련 메소드를 전부 담당하는 클래스
public class ClientSocket extends Socket{
	private ClientAddress address = null;
	public ObjectOutputStream oos = null;
	public ObjectInputStream ois = null;
	private Stack<Exception> errorList = null;
	ClientThread thread = null;
	
	public ClientSocket(ClientAddress address) throws IOException {
		this.address = address;
		connection();
	}
	
	/**
	 * 서버 접속 메소드
	 */
	private void connection() throws IOException {
		super.connect(address);
		oos = new ObjectOutputStream(getOutputStream());
		ois = new ObjectInputStream(getInputStream());
		thread = new ClientThread(this);
		thread.start();
		//구분을 줘서 서버연결 성공 시 쓰레드 실행, 서버 연결 불가시 메세지 출력
		
	}
	/**
	 *  요청 전송 메소드
	 *  ProtocolNumber, String 입력 시 자동 전송
	 */
	public void send(String... str) throws IOException {
		String msg = "";
		for(int i=0;i<str.length;i++) {
			if(i==str.length-1) 
				msg = msg+str[i];
			else 
				msg = msg+str[i]+Protocol.seperator;				
		}
		oos.writeObject(msg);
	}
	/**
	 *  로그인 시도 메소드
	 * @throws IOException 
	 */
	public void checkLogin(String id, String pw) throws IOException {
		send(Protocol.checkLogin, id, pw);
	}
	/**
	 *  회원가입 메소드
	 */
	public void addUser(String new_id, String new_pw, String new_name) throws IOException{
		send(Protocol.addUser, new_id, new_pw, new_name);
	}
	/**
	 *  메소드
	 */
	
	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */

	/**
	 *  메소드
	 */
	
}
