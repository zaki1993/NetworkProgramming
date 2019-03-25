package com.fmi.mpr.e01;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

import static com.fmi.mpr.e01.SocketUtil.BUFFER_SIZE;

public class Server {
	
	private ServerSocket server;
	private boolean isRunning;
	private ExecutorService threadPool;
	
	public Server(int port) throws IOException {
		this.server = new ServerSocket(port);
		this.threadPool = Executors.newFixedThreadPool(10);
	}
	
	public void start() {
		if (!isRunning) {
			this.isRunning = true;
			System.out.println("Server is listening on port " + this.server.getLocalPort());
		}
	}
	
	public void stop() {
		if (isRunning) {
			this.isRunning = false;
			SocketUtil.close(this.server);
		}
	}
	
	public void listen() throws IOException {
		while (isRunning) {
			try {
				Socket client = server.accept();
				threadPool.execute(() -> processClient(client));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void processClient(Socket client) {

		try {
			InputStream inData = client.getInputStream();
			//readInput(inData);
			//returnResponse(client.getOutputStream());
			
			acceptFile(inData);
		} catch (IOException e) {
			throw new ServerException(e);
		} finally {
			SocketUtil.close(client);
		}
	}

	private void acceptFile(InputStream inData) throws IOException {
		
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = 0;
		
		File file = new File("received_mpr.jpg");
		
		try (OutputStream fileOut = new FileOutputStream(file)) {
			while ((bytesRead = inData.read(buffer, 0, BUFFER_SIZE)) > 0) {
				fileOut.write(buffer, 0, bytesRead);
			}
			fileOut.flush();
		}
		System.out.println("Accepted file from client..");
	}

	private void returnResponse(OutputStream outputStream) throws IOException {
		outputStream.write("Hello to you".getBytes());
	}

	private void readInput(InputStream inData) throws IOException {	
		
		byte[] buffer = new byte[BUFFER_SIZE]; int bytesRead = 0;
  
		while ((bytesRead = inData.read(buffer, 0, BUFFER_SIZE)) > 0) {
			System.out.println(new String(buffer, 0, bytesRead));
			if (bytesRead < BUFFER_SIZE) {
				break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {

		Server s = new Server(8080);
		try {
			if (s != null) {
				s.start();
				s.listen();
			}
		} finally {
			if (s != null) {
				s.stop();
			}
		}
	}
}
