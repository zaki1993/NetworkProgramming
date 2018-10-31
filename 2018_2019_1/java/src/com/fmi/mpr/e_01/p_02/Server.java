package com.fmi.mpr.e_01.p_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fmi.mpr.e_01.Utils;

public class Server {
	
	private ServerSocket listener;
	private boolean isStarted;
	private Map<String, User> users;
	
	
	public Server(int port) throws IOException {
		listener = new ServerSocket(port);
		this.users = new ConcurrentHashMap<>();
	}
	
	public void start() throws IOException {
		
		if (!isStarted) {
			this.isStarted = true;
			System.out.println("Server started running on port " + this.listener.getLocalPort());
			while (isStarted) {
				try {
					listen();
				} catch (Exception e) {
					stop();
				}
			}
		}
		
	}

	private void stop() throws IOException {
		if (listener != null) {
			this.isStarted = false;
			listener.close();
		}
	}

	private void listen() throws Exception {
		
		Socket client = listener.accept();
		
		String userName = getUsername(client.getInputStream());
		
		if (userName != null) {
			if (users.containsKey(userName)) {
				sendMessage(client.getOutputStream(), "Already logged in!");
			} else {
				System.out.println("User " + userName + " logged in");
				users.put(userName, new User(userName, client));
				Thread clientThread = new Thread(()->{
					try {
						String to = readMessage(client.getInputStream());
						if (users.containsKey(to)) {
							String msg = readMessage(client.getInputStream());
							OutputStream toOut = users.get(to).getOutputStream();
							sendMessage(toOut, msg);
						} else {
							sendMessage(client.getOutputStream(), "User is not logged in");
						}
					} catch (IOException e) {
						System.out.println(e.getMessage());
					} finally {
						users.remove(userName);
						Utils.close(client);
					}
				});
				clientThread.start();	
			}
		}
	}

	private String readMessage(InputStream in) {
		
		String line = null;
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			line = reader.readLine();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return line;
	}

	private String getUsername(InputStream inputStream) {
		return readMessage(inputStream);
	}

	private void readInput(InputStream in) throws IOException {
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (SocketException se) {
			System.out.println(se.getMessage());
		}
	}
	
	private void sendMessage(OutputStream out, String msg) {
		
		try (PrintStream ps = new PrintStream(out, true)) {
			ps.println(msg);
		}
	}
}
