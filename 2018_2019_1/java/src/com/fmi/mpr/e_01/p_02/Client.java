package com.fmi.mpr.e_01.p_02;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {
	
	private Socket s;
	
	public Client() {
		this.s = new Socket();
	}
	
	public void connect(String host, int port) throws IOException {
		
		SocketAddress address = new InetSocketAddress(host, port);
		this.s.connect(address);
		
		PrintStream ps = new PrintStream(s.getOutputStream(), true);
		
		Scanner sc = new Scanner(System.in);
		
		String line = null;
		while ((line = sc.nextLine()) != null) {
			ps.println(line);
		}
		
		closeConnection();
	}

	private void closeConnection() throws IOException {
		
		if (this.s != null) {
			this.s.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) {
			throw new IllegalArgumentException("Please specify host and port..!");
		}
		
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		
		Client c = new Client();
		c.connect(host, port);
	}
}
