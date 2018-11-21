package com.fmi.mpr.e_05.p_01;

import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.io.*;

public class Demo {
	
	public static void main(String[] args) throws SocketException {
		
		
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		
		while (interfaces.hasMoreElements()) {
			NetworkInterface intrf = interfaces.nextElement();
			processInterface(intrf);
		}
	}

	private static void processInterface(NetworkInterface intrf) {
		System.out.println();
		System.out.println("name: " + intrf.getName());
		
		Enumeration<InetAddress> addresses = intrf.getInetAddresses();
		
		System.out.println("Internet addresses are: ");
		while (addresses.hasMoreElements()) {
			InetAddress address = addresses.nextElement();
			System.out.println(address);
		}
		
		List<InterfaceAddress> iAddresses = intrf.getInterfaceAddresses();
		iAddresses.forEach(System.out::println);
		
	}
}
