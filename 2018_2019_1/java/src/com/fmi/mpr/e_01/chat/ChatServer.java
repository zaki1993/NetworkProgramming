package com.fmi.mpr.e_01.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	
	private ServerSocket listener;
	private boolean running;
	
	private ChatServer(int port) throws IOException {
		listener = new ServerSocket(port);
	}
	
	private void start() {
		
		if (!running) {
			this.running = true;
			System.out.println("Server is running");
			while (running) {
				try {
					listen();
				} catch (Exception e) {
					stop();
				}
			}
		}
	}
	
	private void stop() {
		// TODO Auto-generated method stub
		
	}

	private void listen() throws IOException {
		
		try (Socket clientOne = listener.accept();
			 Socket clientTwo = listener.accept()) {
			
			InputStream clientOneIn = clientOne.getInputStream();
			OutputStream clientOneOut = clientOne.getOutputStream();
			
			InputStream clientTwoIn = clientTwo.getInputStream();
			OutputStream clientTwoOut = clientTwo.getOutputStream();
			
			//doAction(clientOneIn, clientTwoOut);
			//doAction(clientTwoIn, clientOneOut);
			
			Thread read = new Thread(() -> {
				doAction(clientOneIn, clientTwoOut);
			});
			read.start();
			
			Thread write = new Thread(() -> {
				doAction(clientTwoIn, clientOneOut);
			});
			write.start();
			
			try {
				read.join();
				write.join();
			} catch (Exception e) {
				
			}
		}	
	}

	private void doAction(InputStream in, OutputStream out) {
		
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			PrintStream writer = new PrintStream(out, true);
			String line = null;
			while ((line = reader.readLine()) != null) {
				writer.println(line);
			}
		} catch (IOException e) {}
	}

	public static void main(String[] args) throws IOException {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Expected 1 argument[port]!");
		}
		int port = Integer.parseInt(args[0]);
		ChatServer chatServer = new ChatServer(port);
		chatServer.start();
		
	}

}
