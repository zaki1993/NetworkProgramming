package com.fmi.mpr.e02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UDPClient {
	
	private DatagramSocket client;
	private static final int MAX_BUFFER_SIZE = 4096;
	
	public UDPClient() throws SocketException {
		this.client = new DatagramSocket();
	}
	
	public void send(DatagramPacket packet) throws IOException {
		this.client.send(packet);
	}
	
	public static void main(String[] args) throws IOException {
		
		UDPClient c = new UDPClient();
		/*
		 * byte[] data = constructData(); InetAddress address =
		 * InetAddress.getByName("localhost"); int port = 8080; DatagramPacket p = new
		 * DatagramPacket(data, 0, data.length, address, port);
		 * 
		 * for (int i = 0; i < 100; i++) { c.send(p); }
		 */
		c.sendFile();
	}

	private static byte[] constructData() {
		return "Hello world".getBytes();
	}
	
	public void sendFile() throws FileNotFoundException, IOException {
		
		File f = new File("video1.mp4");
		
		try (InputStream fileIn = new FileInputStream(f)) {

			InetAddress address = InetAddress.getByName("localhost");
			int port = 8080;
			byte[] fileLength = longToBytes(f.length());
			System.out.println(fileLength.length);
			int serverRespSize = "SUCCESS".length();
			DatagramPacket serverResponse = new DatagramPacket(new byte[serverRespSize], 0, serverRespSize, address, port);
			String response = "FAILURE";
			do {
				DatagramPacket pSize = new DatagramPacket(fileLength, 0, fileLength.length, address, port);
				client.send(pSize);
				client.receive(serverResponse);
			} while (serverResponse.getLength() != serverRespSize && response.equals("SUCCESS"));
			
			byte[] data = new byte[MAX_BUFFER_SIZE];
			int bytesRead = 0;
			
			while ((bytesRead = fileIn.read(data, 0, MAX_BUFFER_SIZE)) > 0) {
				DatagramPacket p = new DatagramPacket(data, 0, bytesRead, address, port);
				client.send(p);
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		}
	}
	
	public byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
}
