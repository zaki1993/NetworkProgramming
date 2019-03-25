package com.fmi.mpr.e02;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.fmi.mpr.e01_2.ServerUtils;

public class UDPServer {
	
	private DatagramSocket server;
	private boolean isStarted;
	
	private static final int MAX_BUFFER_SIZE = 4096;
	
	public UDPServer(int port) throws SocketException {
		server = new DatagramSocket(port);
	}
	
	public void start() {
		if (!isStarted) {
			this.isStarted = true;
		}
	}
	
	public void stop() {
		if (isStarted) {
			this.isStarted = false;
			ServerUtils.close(server);
		}
	}
	
	public void listen() throws IOException {
		/*
		 * while (isStarted) { try {
		 */
				/*
				 * receive(p);
				 * 
				 * byte[] data = p.getData(); int offset = p.getOffset(); int length =
				 * p.getLength(); InetAddress address = p.getAddress();
				 * 
				 * System.out.println("Received package from " + address.getHostName() +
				 * ". The data is: " + new String(data, offset, length));
				 */
				receiveFile();
		/*
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
		//}
	}
	
	public void receiveFile() throws IOException {
				
		File f = new File("video.mp4");
		
		try (OutputStream fileOut = new FileOutputStream(f)) {
			
			DatagramPacket p = new DatagramPacket(new byte[8], 0, 8);
			receive(p);
			long fileSize = bytesToLong(p.getData());
			System.out.println("File size is: " + fileSize);
			
			byte[] response = "SUCCESS".getBytes();
			p = new DatagramPacket(response, 0, response.length, p.getAddress(), p.getPort());
			send(p);
			
			p = new DatagramPacket(new byte[MAX_BUFFER_SIZE], MAX_BUFFER_SIZE);
			
			int bytesReceived = 0;	
			long totalBytesReceived = 0L;
			do {
				receive(p);
				byte[] data = p.getData();
				int offset = p.getOffset();
				int length = p.getLength();
				bytesReceived = length;
				fileOut.write(data, offset, length);
				totalBytesReceived += bytesReceived;
				//System.out.println("Received package from " + p.getAddress().getHostName() + ". The data is: " + new String(data, offset, length));
				//System.out.println(new String(data, offset, length));
				System.out.println(totalBytesReceived + " " + fileSize);
			} while (totalBytesReceived < fileSize);
			
			fileOut.flush();
			System.out.println("Total bytes received: " + totalBytesReceived + ". File path is: " + f.getAbsolutePath());
		}
	}
	
	public void send(DatagramPacket packetToSend) throws IOException {
		this.server.send(packetToSend);
	}
	
	public void receive(DatagramPacket packetToReceive) throws IOException {
		this.server.receive(packetToReceive);
	}
	
	public static void main(String[] args) throws SocketException {
		
		UDPServer s = new UDPServer(8080);
		try {
			s.start();
			s.listen();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.stop();
		}
		
	}

	public long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();//need flip 
	    return buffer.getLong();
	}
}
