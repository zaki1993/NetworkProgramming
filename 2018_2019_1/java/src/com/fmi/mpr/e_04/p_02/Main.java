package com.fmi.mpr.e_04.p_02;

import java.net.*;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		DatagramSocket s = new DatagramSocket(8888);
		
		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
		s.receive(packet);
		
		byte[] data = packet.getData();
		InetAddress from = packet.getAddress();
		
		SocketAddress sa = packet.getSocketAddress();

		System.out.println("InetAddress: " + from.toString());
		System.out.println("SocketAddress: " + sa.toString());
		
		int port = packet.getPort();
		
		DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
		
		response.setAddress(from);
		response.setPort(port);
		response.setData("test".getBytes());
		
		s.send(response);
	}
}
