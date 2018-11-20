package com.fmi.mpr.e_05.p_01;

import java.net.*;
import java.io.*;

public class MulticastServer {
	
	public static void sendUDPMessage(String message, String ip, int port) throws IOException {
		
		DatagramSocket socket = new DatagramSocket();
		
		InetAddress group = InetAddress.getByName(ip);
		
		byte[] msg = message.getBytes();
		
		DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
		
		socket.send(packet);
		socket.close();
	}
	
	public static void main(String[] args) throws IOException {
		
		sendUDPMessage("This is a multicast messge", "230.0.0.1", 8888);
		sendUDPMessage("This is the second multicast messge", "230.0.0.1", 8888);
		sendUDPMessage("This is the third multicast messge", "230.0.0.1", 8888);
		sendUDPMessage("END", "230.0.0.1", 8888);
	}
}
