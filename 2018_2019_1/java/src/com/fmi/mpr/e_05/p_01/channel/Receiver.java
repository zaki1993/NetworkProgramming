package com.fmi.mpr.e_05.p_01.channel;

import java.nio.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;

public class Receiver {
   
	private static final String MULTICAST_INTERFACE = "enp4s0f1";
	private static final int    MULTICAST_PORT = 4321;
	private static final String MULTICAST_IP = "230.0.0.0";
   
	private String receiveMessage(String ip, String iface, int port) throws IOException {
		
		DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET);
	    NetworkInterface networkInterface = NetworkInterface.getByName(iface);
	    
	    datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
	    datagramChannel.bind(new InetSocketAddress(port));
	    datagramChannel.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);
	    
	    InetAddress inetAddress = InetAddress.getByName(ip);
	    MembershipKey membershipKey = datagramChannel.join(inetAddress, networkInterface);
	    
	    System.out.println("Waiting for the message...");
	    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
	    
	    datagramChannel.receive(byteBuffer);
	    byteBuffer.flip();
	    
	    byte[] bytes = new byte[byteBuffer.limit()];
	    byteBuffer.get(bytes, 0, byteBuffer.limit());
	    membershipKey.drop();
	    
	    return new String(bytes);
	}
	
	public static void main(String[] args) throws IOException {
	
		Receiver mr = new Receiver();
	    System.out.println("Message received : " + mr.receiveMessage(MULTICAST_IP, MULTICAST_INTERFACE, MULTICAST_PORT));
	}
}
