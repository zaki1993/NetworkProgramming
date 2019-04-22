package com.fmi.mpr.e04_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastClient {
	
	public void sendUDPMessage(String msg, String host, int port) throws IOException {
		
		DatagramSocket ds = new DatagramSocket();
		
		byte[] buf = msg.getBytes();
		DatagramPacket p = new DatagramPacket(buf, buf.length);
		
		InetAddress a = InetAddress.getByName(host);
		p.setAddress(a);
		p.setPort(port);
		
		ds.send(p);
		
		DatagramPacket r = new DatagramPacket(new byte[1024], 1024);
		ds.receive(r);
		
		System.out.println(new String(r.getData(), r.getOffset(), r.getLength()));
	}
	
	public static void main(String[] args) throws IOException {
		new MulticastClient().sendUDPMessage("Hello world", "224.0.1.0", 8080);
	}
}
