package com.fmi.mpr.e_04.p_02.file;

import java.net.*;
import java.io.*;

public class MackaTransferClient {
	
	private DatagramSocket client;
	
	public MackaTransferClient() throws SocketException {
		this.client = new DatagramSocket();
	}
	
	public void sendMacka() throws IOException {
		
		File macka = new File("../kotka.png");
		FileInputStream in = new FileInputStream(macka);
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		
		InetAddress to = InetAddress.getByName("localhost");
		int port = 8888;
		
		while ((bytesRead = in.read(buffer, 0, 1024)) > 0) {
			System.out.println("bytes send " + bytesRead);
			DatagramPacket packet = new DatagramPacket(new byte[bytesRead], bytesRead);
			packet.setAddress(to);
			packet.setPort(port);
			packet.setData(buffer, 0, bytesRead);
			
			client.send(packet);
		}
	}
	
	public static void main(String[] args) throws SocketException, IOException {
		new MackaTransferClient().sendMacka();
	}
}
