package com.fmi.mpr.e_04.p_01;

import java.net.*;
import java.io.*;

public class UDPServer {
	
	private DatagramSocket server;
	private static final int BUFFER_SIZE = 4096;
	
	public UDPServer() throws SocketException {
		server = new DatagramSocket(8888);
	}
	
	public void listen() throws IOException {
		
		while (true) {
			
			DatagramPacket request = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			server.receive(request);
			
			String info = new String(request.getData()).trim();
			int length = request.getLength();
			InetAddress from = request.getAddress();
			int port = request.getPort();
			
			System.out.println(from.toString() + ":" + port + " send " + info + " with length " + length);
			
			DatagramPacket response = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			response.setAddress(from);
			response.setPort(port);
			
			String responseString = "zdrkpbepce";
			response.setData(responseString.getBytes());
			server.send(response);
		}
	}
	
	public static void main(String[] args) throws IOException {
		new UDPServer().listen();
	}
}
