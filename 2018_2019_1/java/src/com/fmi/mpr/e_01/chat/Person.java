package com.fmi.mpr.e_01.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Person extends Thread {
	
	private InputStream in;
	private OutputStream out;
	
	public Person(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
	}
	
	public void sendMessage() throws IOException {
		
		PrintStream ps = new PrintStream(out, true);
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		ps.println(line);
	}
	
	public void readMessage() throws IOException {
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(in));
		String line = sc.readLine();
		System.out.println(line);
	}
	
	@Override
	public void run() {
		/*while (true) {
			Thread read = new Thread(()->while(true) {sendMessage();});
		}*/
	}
}
