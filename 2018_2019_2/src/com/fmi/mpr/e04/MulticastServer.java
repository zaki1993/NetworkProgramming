package com.fmi.mpr.e04;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.fmi.mpr.e01_2.ServerUtils;

public class MulticastServer {

	private MulticastSocket multicastUdpClient;
	
	public MulticastServer(int port) throws IOException {
		multicastUdpClient = new MulticastSocket(port);
	}
	
	public void receive(String host) throws IOException {
		/*
		 * try { while (true) {
		 */
		receiveUDPMessage(host);
		/*
		 * } } finally { close(); }
		 */
	}
	
	private void receiveUDPMessage(String host) throws IOException {
		
		join(host);
		receiveUDPMessageInternal(host);
		leave(host);
	}
	
	private void receiveUDPMessageInternal(String host) throws IOException {
		
		InetAddress group = InetAddress.getByName(host);

		byte[] buf = new byte[1024];
		DatagramPacket p = new DatagramPacket(buf, buf.length);
		multicastUdpClient.receive(p);
	
		System.out.println(new String(p.getData(), p.getOffset(), p.getLength()));
	}

	private void join(String host) throws IOException {
		InetAddress group = InetAddress.getByName(host);
		multicastUdpClient.joinGroup(group);
	}
	
	private void leave(String host) throws IOException {
		InetAddress group = InetAddress.getByName(host);
		multicastUdpClient.leaveGroup(group);
	}
	
	private void close() {
		ServerUtils.close(multicastUdpClient);
	}
	
	public static void main(String[] args) throws IOException {
		new MulticastServer(8080).receive("230.0.0.1");
	}
}
