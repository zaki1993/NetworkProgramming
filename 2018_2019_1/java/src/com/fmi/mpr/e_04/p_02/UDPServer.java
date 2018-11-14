package com.fmi.mpr.e_04.p_02;

import java.net.*;

import com.fmi.mpr.e_04.p_02.client.Person;

import java.io.*;

public class UDPServer {

	private DatagramSocket server;
	
	public UDPServer() throws SocketException {
		this.server = new DatagramSocket(8888);
	}
	
	public void readObjects() throws IOException, ClassNotFoundException {
		
		while (true) {
			
			byte[] buffer = new byte[4096];
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			
			server.receive(request);
			
			ByteArrayInputStream byteIn = new ByteArrayInputStream(request.getData());
			ObjectInputStream objectReader = new ObjectInputStream(byteIn);
			
			Person x = (Person) objectReader.readObject();
			
			System.out.println(x.getFirstName() + " " + x.getLastName() + " " + x.getAge() + " " + x.getNumber());
		}
	}
	
	public static void main(String[] args) throws SocketException, IOException, ClassNotFoundException {
		new UDPServer().readObjects();
	}
}
