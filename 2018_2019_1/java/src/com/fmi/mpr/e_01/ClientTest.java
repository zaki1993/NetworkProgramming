package com.fmi.mpr.e_01;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class ClientTest {
	public static void main(String[] args) throws IOException {
		
		Socket client = new Socket();
		SocketAddress address = new InetSocketAddress("localhost", 8888);
		client.connect(address);
		
		System.out.println("Connected to server");
		
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		PrintStream ps = new PrintStream(client.getOutputStream(), true);
		ps.println(line);
	}
}
