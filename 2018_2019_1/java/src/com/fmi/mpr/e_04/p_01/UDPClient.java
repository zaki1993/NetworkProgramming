package com.fmi.mpr.e_04.p_01;

import java.net.*;
import java.time.LocalDateTime;
import java.io.*;

public class UDPClient {
	
	private static final int BUFFER_SIZE = 4096;
	
	private DatagramSocket client;
	
	public UDPClient() throws SocketException {
		client = new DatagramSocket();
	}
	
	public void sendAndReceive() throws IOException {
		
		try {
			DatagramPacket request = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			InetAddress to = InetAddress.getByName("localhost");
			int port = 8888;
			request.setAddress(to);
			request.setPort(port);
			request.setData(LocalDateTime.now().toString().getBytes());
			
			// send package to server
			client.send(request);
			
			DatagramPacket response = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			client.receive(response);
			
			String responseString = new String(response.getData()).trim();
			System.out.println("Server responded with: " + responseString);
		} finally {
			client.close();
		}
	}
	
	public static void main(String[] args) throws SocketException, IOException {
		
		new UDPClient().sendAndReceive();
	}
}
