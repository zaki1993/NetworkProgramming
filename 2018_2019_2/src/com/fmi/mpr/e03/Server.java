package com.fmi.mpr.e03;

import java.io.IOException;
import java.net.*;

import com.fmi.mpr.e01_2.ServerUtils;

public class Server {
	
	private ServerSocket server;
	
	public Server(int port) throws IOException {
		this.server = new ServerSocket(port);
	}
	
	public void listen() throws IOException {
		while (true) {
			System.out.println("Listening for connections");
			Socket client = this.server.accept();
			new Thread(() -> {
				try {
					new URLReader(client).process();
					ServerUtils.close(client);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
	
	public static void main(String[] args) throws IOException {
		new Server(8888).listen();
	}
}
