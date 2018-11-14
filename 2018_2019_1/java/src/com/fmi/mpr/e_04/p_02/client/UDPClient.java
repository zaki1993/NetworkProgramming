package com.fmi.mpr.e_04.p_02.client;

import java.net.*;
import java.time.LocalDateTime;
import java.io.*;

public class UDPClient {
	
	private DatagramSocket client;
	
	public UDPClient() throws SocketException {
		this.client = new DatagramSocket();
	}
	
	public void sendInfo() throws IOException {
		
		byte[] info = LocalDateTime.now().toString().getBytes();

		InetAddress to = InetAddress.getByName("localhost");
		int port = 8888;
		
		DatagramPacket p = new DatagramPacket(info, info.length, to, port);
		client.send(p);
		
		System.out.println(new String(info) + " was send to the server");
		
		DatagramPacket response = new DatagramPacket(new byte[100], 100);
		client.receive(response);
		
		System.out.println("Received from server: " + new String(response.getData()));
	}
	
	public void sendObjects() throws IOException {
		
		for (int i = 0; i < 100; i++) {

			Person p = new Person("Ivan", "Ivanov");
			sendPerson(p);
		}
	}
	
	private void sendPerson(Person p) throws IOException {
		DatagramPacket response = new DatagramPacket(new byte[4096], 4096, InetAddress.getByName("localhost"), 8888);
		
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		
		ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
		objectOut.writeObject(p);
		objectOut.close();
		
		response.setData(byteOut.toByteArray());
		client.send(response);
	}
	
	public static void main(String[] args) throws SocketException, IOException {
		//new UDPClient().sendInfo();
		new UDPClient().sendObjects();
	}
}
