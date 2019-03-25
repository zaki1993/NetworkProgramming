package com.fmi.mpr.e02_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Demo {
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = new DatagramSocket();
		
		byte[] data = "Hello world".getBytes();
		
		InetAddress address = InetAddress.getLocalHost();
		int port = 8080;
		
		DatagramPacket p = new DatagramPacket(data, data.length, address, port);
		ds.send(p);
	}
}
