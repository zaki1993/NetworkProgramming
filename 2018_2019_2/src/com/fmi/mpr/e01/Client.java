package com.fmi.mpr.e01;

import java.net.*;
import java.io.*;

import static com.fmi.mpr.e01.SocketUtil.BUFFER_SIZE;

public class Client {
	
	private Socket client;
	
	public Client(String host, int port) throws IOException {
		this.client = new Socket(host, port);
	}
	
	public void sendData(String data) throws IOException {
		
		if (client != null && !client.isClosed()) {
			OutputStream out = this.client.getOutputStream();
			out.write(data.getBytes());
			out.flush();
		}
	}
	
	public void readResponse() throws IOException {
		
		if (client != null && !client.isClosed()) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(this.client.getInputStream()))) {
				System.out.println(br.readLine());
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Client c = new Client("localhost", 8080);
		try {
			//c.sendData("Hello world 2!");
			//c.readResponse();
			c.sendFile("kotka.jpg");
		} finally {
			SocketUtil.close(c.client);
		}
	}

	private void sendFile(String fileName) throws IOException {
		
		File file = new File(fileName);
		
		try (InputStream fileIn = new FileInputStream(file)) {
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = 0;
			
			OutputStream serverOut = client.getOutputStream();
			
			while ((bytesRead = fileIn.read(buffer, 0, BUFFER_SIZE)) > 0) {
				serverOut.write(buffer, 0, bytesRead);
			}
			serverOut.flush();
			System.out.println("Client send the file..");
		}
	}
}
