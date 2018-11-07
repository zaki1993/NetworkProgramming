package com.fmi.mpr.e_01.p_02;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class User {
	
	private String userName;
	private Socket s;
	
	public User(String userName, Socket s) {
		this.userName = userName;
		this.s = s;
	}
	
	public OutputStream getOutputStream() throws IOException {
		return s.getOutputStream();
	}
}
