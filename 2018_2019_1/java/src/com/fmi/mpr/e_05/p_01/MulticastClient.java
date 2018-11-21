package com.fmi.mpr.e_05.p_01;

import java.net.*;
import java.io.*;

public class MulticastClient implements Runnable {
	
	public static void main(String[] args) {
		
		Thread t = new Thread(new MulticastClient());
		t.start();
	}

	public void receiveUDPMessage(String ip, int port) throws IOException {
      
		byte[] buffer = new byte[1024];
		MulticastSocket socket = new MulticastSocket(port);
		
		InetAddress group = InetAddress.getByName(ip);
		socket.joinGroup(group);
		
		while(true) {
			System.out.println("Waiting for multicast message...");
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			
			String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
			System.out.println("Multicast UDP message received: " + msg);
			
			if("END".equals(msg)) {
				System.out.println("No more messages. Exiting : " + msg);
				break;
			}
		}
		socket.leaveGroup(group);
		socket.close();
	}

	@Override
	public void run(){
		
		try {
			receiveUDPMessage("230.0.0.1", 8888);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
