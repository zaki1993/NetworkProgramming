package com.fmi.mpr.e_05.p_01;

import java.net.*;
import java.io.*;

public class HttpServer {
	
	private ServerSocket server;
	private boolean isRunning;
	
	public HttpServer() throws IOException {
		this.server = new ServerSocket(8888);
	}
	
	public void start() {
		
		if (!isRunning) {
			this.isRunning = true;
			run();
		}
	}
	
	private void run() {
		
		while(isRunning) {
			
			try {
				listen();
			} catch (Exception e) {
				// parse exception
				e.printStackTrace();
			}
		}
	}

	public void listen() throws IOException {
		
		Socket client = null;
		try {
			client = server.accept();
			System.out.println(client.getInetAddress() + " connected..!");
			
			HttpClient httpClient = new HttpClient(client);
			httpClient.receive();
			httpClient.send();
			
			System.out.println("Connection to " + client.getInetAddress() + " closed..!");
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		new HttpServer().start();
	}
}
