package com.fmi.mpr.e01_2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Demo {
	public static void main(String[] args) throws IOException {
		
		Socket socket = new Socket();
		
		InetAddress localhostAddress = InetAddress.getByName("localhost");
		SocketAddress address = new InetSocketAddress(localhostAddress, 8080);
		socket.bind(address);
		socket.getOutputStream();
	}
}
