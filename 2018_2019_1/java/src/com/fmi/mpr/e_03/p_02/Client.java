package com.fmi.mpr.e_03.p_02;

import java.net.*;
import java.io.*;

public class Client {
	
	public static void main(String[] args) throws IOException {
		
		Socket s = new Socket();
		
		SocketAddress address = new InetSocketAddress("localhost", 8888);
		s.connect(address);
		
		OutputStream out = s.getOutputStream();
		out.write("test.html\r\n".getBytes());
		
		File file = new File("../test.html");
		FileInputStream fileIn = new FileInputStream(file);
		
		byte[] buffer = new byte[4096];
		int bytesRead = 0;
		
		while ((bytesRead = fileIn.read(buffer, 0, 4096)) > 0) {
			out.write(buffer, 0, bytesRead);
		}
		out.flush();
	}
}
