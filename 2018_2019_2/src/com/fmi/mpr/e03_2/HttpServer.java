package com.fmi.mpr.e03_2;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class HttpServer {
	
	private ServerSocket server;
	
	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	public HttpServer(int port) throws IOException {
		this.server = new ServerSocket(port);
	}
	
	public void listen() throws IOException {
		while (true) {
			Socket client = server.accept();
			threadPool.submit(new HttpCalculatorClient(client));
		}
	}
	
	public static void main(String[] args) throws IOException {
		new HttpServer(8888).listen();
	}
}
