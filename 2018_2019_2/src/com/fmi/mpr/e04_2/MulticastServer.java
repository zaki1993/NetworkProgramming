package com.fmi.mpr.e04_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class MulticastServer {
	
	private int port;
	private String host;
	private MulticastSocket ms;
	
	public MulticastServer(String host, int port) throws IOException {
		this.port = port;
		this.host = host;
		SocketAddress sa = new InetSocketAddress(host, port);
		ms = new MulticastSocket(port);
	}
	
	public void listen() throws IOException {
		while (true) {
			receiveUDPMessage();
		}
	}

	private void receiveUDPMessage() throws IOException {
		
		InetAddress group = InetAddress.getByName(host);
		ms.joinGroup(group);
		
		// receive message
		DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
		ms.receive(p);
		System.out.println("Server received: " + new String(p.getData(), p.getOffset(), p.getLength()));
		
		DatagramPacket r = new DatagramPacket("OK".getBytes(), 2);
		r.setAddress(p.getAddress());
		r.setPort(p.getPort());
		
		ms.send(r);
		
		
		ms.leaveGroup(group);
	}
	
	public static void main(String[] args) throws IOException {
		new MulticastServer("224.0.1.0", 8080).listen();
	}
}
