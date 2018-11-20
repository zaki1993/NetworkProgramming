package com.fmi.mpr.e_05.p_01.channel;

import java.nio.*;
import java.nio.channels.*;
import java.io.IOException;
import java.net.*;

public class Publisher {
	private static final String MULTICAST_INTERFACE = "enp4s0f1";
	private static final int    MULTICAST_PORT = 4321;
	private static final String MULTICAST_IP = "230.0.0.0";
	
	public void sendMessage(String ip, String iface, int port, String message) throws IOException {
		
	    DatagramChannel datagramChannel = DatagramChannel.open();
	    datagramChannel.bind(null);
	    NetworkInterface networkInterface = NetworkInterface.getByName(iface);
	    datagramChannel.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);
	    ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
	    InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
	    datagramChannel.send(byteBuffer, inetSocketAddress);
	    datagramChannel.close();
	}
	
	public static void main(String[] args) throws IOException {
		
		Publisher p = new Publisher();
		p.sendMessage(MULTICAST_IP, MULTICAST_INTERFACE, MULTICAST_PORT, "Hi there!");
	}
}
