package com.fmi.mpr.e_01.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Person {
	
	private Socket s;
	
	public Person(Socket s) {
		this.s = s;
	}
	
	public void sendMessage() throws IOException {
		
		PrintStream ps = new PrintStream(s.getOutputStream(), true);
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		ps.println(line);
	}
	
	public void readMessage() throws IOException {
		
		Scanner sc = new Scanner(s.getInputStream());
		String line = sc.nextLine();
		System.out.println(line);
	}
}
