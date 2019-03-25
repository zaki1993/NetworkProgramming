package com.fmi.mpr.e02_2;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class UDPServer {
	private DatagramSocket server;
	
	public UDPServer(int port) throws SocketException {
		this.server = new DatagramSocket(port);
	}
	
	public void read() throws IOException {
		
		byte[] data = new byte[4];
		
		DatagramPacket p = new DatagramPacket(data, 4);
		server.receive(p);
		
		System.out.println(new String(data, p.getOffset(), p.getLength()));
	}
	
	public void readObject() throws IOException, ClassNotFoundException {
		byte[] data = new byte[1024];

		DatagramPacket p = new DatagramPacket(data, 1024);
		server.receive(p);
		
		ByteArrayInputStream inBytes = new ByteArrayInputStream(data);
		ObjectInputStream objectIn = new ObjectInputStream(inBytes);
		Object o = objectIn.readObject();
		
		if (o instanceof Person) {
			Person person = (Person) o;
			System.out.println("My name is " + person.getName());
		}
	}
	
	public void readFile() throws FileNotFoundException, IOException {
		
		File f = new File("pesho.mp4");
		
		try (OutputStream out = new FileOutputStream(f)) {
			
			// read file size
			DatagramPacket fileSizePacket = new DatagramPacket(new byte[8], 8);
			server.receive(fileSizePacket);
			
			long fileSize = bytesToLong(fileSizePacket.getData());
			
			byte[] response = null;
			
			if (fileSize == -1) {
				response = "SUCCESS".getBytes();
			} else {
				response = "FAILURE".getBytes();
			}
			// send confirmation that we received the packet
			DatagramPacket fileSizeResponse = new DatagramPacket(response, response.length, fileSizePacket.getAddress(), fileSizePacket.getPort());
			server.send(fileSizeResponse);
			
			if (fileSize == -1) {
				return;
			}
			
			byte[] data = new byte[4096];
			DatagramPacket filePacket = new DatagramPacket(data, 4096);
			
			long totalBytesReceived = 0L;
			
			do {
				server.receive(filePacket);
				int length = filePacket.getLength();
				int offset = filePacket.getOffset();
				totalBytesReceived += length;
				out.write(data, offset, length);
			} while(totalBytesReceived < fileSize);
			
			out.flush();
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		UDPServer s = new UDPServer(8080);
		s.readObject();
	}
	

	public long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();//need flip 
	    return buffer.getLong();
	}
}
