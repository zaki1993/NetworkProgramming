package com.fmi.mpr.e_04.p_01;
import java.net.*;
import java.time.LocalDateTime;
import java.io.*;
public class FileTransferUDP {
	
	public static void main(String[] args) throws InterruptedException, SocketException {
		
		FileServer server = new FileServer();
		server.start();
		FileClient client = new FileClient();
		client.start();
		
		server.join();
		client.join();
	}
}

class FileServer extends Thread {
		
	private DatagramSocket server;
	
	public FileServer() throws SocketException {
		server = new DatagramSocket(8888);
	}
	
	@Override
	public void run() {

		try {
			File f = new File("extreme_copy");
			FileOutputStream out = new FileOutputStream(f);
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			int bytesReceived = 0;
			do {
				server.receive(packet);
				out.write(packet.getData());
				bytesReceived = packet.getData().length;
			} while (packet != null && bytesReceived > 0 && bytesReceived <= 1024);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class FileClient extends Thread {
	
	private DatagramSocket client;
	
	public FileClient() throws SocketException {
		client = new DatagramSocket();
	}

	@Override
	public void run() {
		
		File f = new File("extreme");
		
		try (FileInputStream in = new FileInputStream(f)) {
			
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			
			InetAddress address = InetAddress.getByName("localhost");
			int port = 8888;
			
			while ((bytesRead = in.read(buffer, 0, 1024)) > 0) {
				DatagramPacket packet = new DatagramPacket(new byte[1024], bytesRead, address, port);
				packet.setData(buffer, 0, bytesRead);
				client.send(packet);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}