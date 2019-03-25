package com.fmi.mpr.e01_2;

import java.net.*;
import java.io.*;

public class Client {
	
	private Socket client;
	
	public Client(String host, int port) throws IOException {
		this.client = new Socket(host, port);
	}
	
	public void sendData(String data) throws IOException {
		
		if (client != null && !client.isClosed()) {
			PrintWriter serverOut = new PrintWriter(client.getOutputStream(), true);
			serverOut.println(data);
		}
	}
	
	public void sendFile(String fileName) throws IOException {
		
		File file = new File(fileName);
		
		byte[] buffer = new byte[ServerUtils.BUFFER_SIZE];
		int bytesRead = 0;
		
		try (InputStream fileIn = new FileInputStream(file)) {
			OutputStream serverOut = client.getOutputStream();
			while ((bytesRead = fileIn.read(buffer, 0, ServerUtils.BUFFER_SIZE)) > 0) {
				serverOut.write(buffer, 0, bytesRead);
			}
			serverOut.flush();
		}
		System.out.println("Izpratih ti faila..!");
	}
	
	public static void main(String[] args) throws IOException {
		/*
			Client c = null;
			try {
				c = new Client("localhost", 8080);
				for (int i = 0; i < 10; i++) {
					c.sendData("Hello world " + i);
				}
				c.sendData("===EOF===");
				c.readResponse();
			} finally {
				ServerUtils.close(c.client);
			}
			*/
		Client c = null;
		try {
			c = new Client("localhost", 6666);
			c.sendFile("video.mp4");
		} finally {
			ServerUtils.close(c.client);
		}
	}

	private void readResponse() throws IOException {
		
		if (client != null && !client.isClosed()) {
			InputStream inData = client.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inData));
			String line = null;
			System.out.println("Server response: ");
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
}
