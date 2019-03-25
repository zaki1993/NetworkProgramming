package com.fmi.mpr.e02_2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;

public class UDPClient {
	
	private DatagramSocket client;
	
	public UDPClient() throws SocketException {
		this.client = new DatagramSocket();
	}
	
	public void send() throws IOException {
		
		byte[] data = "Hello world".getBytes();
		
		InetAddress address = InetAddress.getLocalHost();
		int port = 8080;
		
		DatagramPacket p = new DatagramPacket(data, data.length, address, port);
		client.send(p);
	}
	
	public void sendObject() throws IOException {
		
		InetAddress address = InetAddress.getLocalHost();
		int port = 8080;
		
		Person person = new Person();
		person.setName("Pesho");
		
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
		objectOut.writeObject(person);
		
		byte[] data = byteOut.toByteArray();
		
		DatagramPacket p = new DatagramPacket(data, data.length, address, port);
		client.send(p);
	}
	
	public void sendFile() throws IOException {
		
		File f = new File("video.mp4");
		long fileSize = f.length();
		
		InetAddress address = InetAddress.getLocalHost();
		int port = 8080;
		
		byte[] fileSizeArray = longToBytes(fileSize);
		
		String responseMsg = null;
		
		do {
			DatagramPacket fileSizePacket = new DatagramPacket(fileSizeArray, 8, address, port);
			client.send(fileSizePacket);
			byte[] response = new byte[7];
			DatagramPacket serverConfirmationPacket = new DatagramPacket(response, response.length);
			client.receive(serverConfirmationPacket);
			
			int offset = serverConfirmationPacket.getOffset();
			int length = serverConfirmationPacket.getLength();
			responseMsg = new String(response, offset, length);
		} while (responseMsg != null);

		if (responseMsg.equals("SUCCESS")) {
			try (InputStream in = new FileInputStream(f)) {
				DatagramPacket filePacket = new DatagramPacket(fileData, fileData.length, address, port);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		UDPClient c = new UDPClient();
		c.sendObject();
	}
	
	public byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
}
