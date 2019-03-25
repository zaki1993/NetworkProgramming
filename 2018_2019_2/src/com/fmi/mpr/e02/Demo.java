package com.fmi.mpr.e02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Demo {
	public static void main(String[] args) throws IOException {
		
		DatagramSocket ds = new DatagramSocket(8080);
		
		InetAddress address = InetAddress.getByName("localhost");
		DatagramPacket p = new DatagramPacket(new byte[] {}, 0, 0, address, 8080);
	
		ds.send(p);
		
		//DatagramPacket p = new DatagramPacket(buf, length);
		
		ds.receive(p);
	}
}
