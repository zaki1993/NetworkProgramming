package com.fmi.mpr.e01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Demo {
	
	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(8080);
		
		Socket s = server.accept();
		
		s.getInputStream();
		
		s.getOutputStream();
		
		s.close();
		
		
		
		
	}
}
