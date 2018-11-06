package com.fmi.mpr.e_02.p_01;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	
	private Socket s;
	
	public Client() throws IOException {
		this.s = new Socket();
		
		SocketAddress address = new InetSocketAddress("localhost", 8888);
		this.s.connect(address);
	}
	
	public void run() throws IOException {
		
		Scanner sc = new Scanner(System.in);
		
		PrintStream ps = new PrintStream(s.getOutputStream(), true);
		
		String line = null;
		while ((line = sc.nextLine()) != null) {
			ps.println(line);
		}
	}
	
	public static void main(String[] args) throws IOException {
		Client c = new Client();
		c.run();
	}
}
