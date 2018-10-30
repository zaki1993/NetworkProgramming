package com.fmi.mpr.e_01.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Expected 1 argument[port]!");
		}
		int port = Integer.parseInt(args[0]);
		Socket s = new Socket();
		SocketAddress address = new InetSocketAddress("localhost", port);
		s.connect(address);
		
		Scanner sc = new Scanner(System.in);
		
		PrintStream ps = new PrintStream(s.getOutputStream(), true);
		ps.println(sc.nextLine());
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		System.out.println(reader.readLine());
		
	}
}
