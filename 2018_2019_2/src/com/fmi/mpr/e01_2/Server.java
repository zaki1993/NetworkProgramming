package com.fmi.mpr.e01_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.fmi.mpr.e01_2.ServerUtils.BUFFER_SIZE;

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
			System.out.println("Server started running on port " + server.getLocalPort());
		}
	}
	
	public void stop() {
		if (isRunning) {
			this.isRunning = false;
			ServerUtils.close(this.server);
			System.out.println("Server is stopped..!");
		}
	}
	
	public void listen() {
		while (isRunning) {
			try {
				Socket client = server.accept();
				threadPool.execute(() -> processClient(client));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void processClient(Socket client) throws ServerException {
		try {
			/*
			 * InputStream inData = client.getInputStream(); readInput(inData);
			 * 
			 * OutputStream outData = client.getOutputStream(); returnResponse(outData);
			 */
			InputStream inData = client.getInputStream();
			receiveFile(inData);
		} catch (IOException e) {
			throw new ServerException(e);
		} finally {
			ServerUtils.close(client);
		}
	}

	private void receiveFile(InputStream inData) throws IOException {
		
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = 0;
		
		File file = new File("video1.mp4");
		
		try (OutputStream out = new FileOutputStream(file)) {
			while ((bytesRead = inData.read(buffer, 0, BUFFER_SIZE)) > 0) {
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
		}
		System.out.println("Poluchih faila ti..!");
	}

	private void returnResponse(OutputStream outData) {
		
		PrintWriter out = new PrintWriter(outData, true);
		out.println("OK, I got your request.");
	}

	private void readInput(InputStream inData) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(inData));
		String line = null;
		while ((line = br.readLine()) != null) {
			
			if (line.equalsIgnoreCase("===EOF===")) {
				break;
			}
			System.out.println(line);
		}
	}
	
	public static void main(String[] args) {
		
		Server server = null;
		try {
			server = new Server(6666);
			server.start();
			server.listen();
		} catch (Exception e) {
			if (server != null) {
				server.stop();
			}
		}
	}
}
