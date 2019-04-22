package com.fmi.mpr.e04;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MulticastClient {
	
	public void sendUDPMessage(String host, int port) throws IOException {

		//SocketAddress sa = new InetSocketAddress(host, port);
		//MulticastSocket ds = new MulticastSocket(8080);
		
		DatagramSocket ds = new DatagramSocket();
		InetAddress to = InetAddress.getByName(host);
		byte[] msg = "Hello world".getBytes();
		DatagramPacket p = new DatagramPacket(msg, msg.length, to, port);
		ds.send(p);
		/*
		 * DatagramPacket r = null; ds.receive(r);
		 */
	}
	
	public static void main(String[] args) throws IOException {
		new MulticastClient().sendUDPMessage("230.0.0.1", 8080);
	}
}
