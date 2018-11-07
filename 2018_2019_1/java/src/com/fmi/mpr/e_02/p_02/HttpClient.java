package com.fmi.mpr.e_02.p_02;

import java.io.*;
import java.net.*;

public class HttpClient {
	
	private Socket client;
	
	public HttpClient() {
		this.client = new Socket();
	}
	
	public void connect(String host, int port) throws IOException {

		SocketAddress address = new InetSocketAddress(host, port);
		this.client.connect(address);
	}
	
	public void transferData() throws IOException {
		
		InputStream in = client.getInputStream();
		OutputStream out = client.getOutputStream();
	}
	
	public static void main(String[] args) throws IOException {
		
		HttpClient c = new HttpClient();
		c.connect("google.bg", 80);
		c.transferData();
		
		//SocketInputStream x;
	}
}
