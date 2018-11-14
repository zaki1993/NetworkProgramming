package com.fmi.mpr.e_04.p_02.file;

import java.net.*;

import org.omg.CORBA.Request;

import java.io.*;

public class MackaServer {
	
	private DatagramSocket server;
	
	public MackaServer() throws SocketException {
		server = new DatagramSocket(8888);
	}
	
	public void getMacka() throws IOException {
		
		File mackaCopy = new File("macka.png");
		FileOutputStream mackaOut = new FileOutputStream(mackaCopy);
		
		byte[] buffer = new byte[1024];
		int lastBytesReceived = 0;
		do {
			DatagramPacket request = new DatagramPacket(buffer, 1024);
			server.receive(request);
			lastBytesReceived = request.getLength();
			mackaOut.write(request.getData(), 0, lastBytesReceived);
			System.out.println("received: " + lastBytesReceived);
		} while (lastBytesReceived == 1024);
		
		mackaOut.flush();
		if (mackaOut != null) {
			mackaOut.close();
		}
	}
	
	public static void main(String[] args) throws SocketException, IOException {
		new MackaServer().getMacka();
	}
}
