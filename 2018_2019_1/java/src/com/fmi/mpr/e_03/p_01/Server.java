package com.fmi.mpr.e_03.p_01;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.*;

public class Server {
	
	private ServerSocket ss;
	private ExecutorService executor;
	
	public Server() throws IOException {
		ss = new ServerSocket(8888);
		executor = Executors.newFixedThreadPool(10);
	}
	
	public void run() throws IOException {
		
		while (true) {
			
			Socket s = ss.accept();
			
			InputStream in = s.getInputStream();
			System.out.println("Client connected");
			
			executor.execute(() -> {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
					String line = null;
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					if (s != null) {
						try {
							s.close();
						} catch (IOException e) {;}
					}
				}
			});
			
			
			/*Thread clientProcesser = new ClientProcesser(s);
			clientProcesser.start();*/
		}
	}

	public static void main(String[] args) throws IOException {
		Server s = new Server();
		s.run();
	}
}
