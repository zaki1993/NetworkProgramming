package com.fmi.mpr.e_05.p_01;

import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;

public class Main {
	
	public static void main(String[] args) throws SocketException {
		
		Enumeration<NetworkInterface> eni = NetworkInterface.getNetworkInterfaces();
		
		while (eni.hasMoreElements()) {
			
			NetworkInterface ni = eni.nextElement();
			
			System.out.println("name: " + ni.getName());
			System.out.println(ni.getDisplayName());
			
			System.out.println("multicast: " + ni.supportsMulticast());
			
			Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
			
			while (inetAddresses.hasMoreElements()) {
				InetAddress addr = inetAddresses.nextElement();
				System.out.println("host: " + addr.getHostAddress());
			}
			
			System.out.println(ni.getInterfaceAddresses());
			
			System.out.println(Arrays.toString(ni.getHardwareAddress()));
			
			System.out.println();
		}
	}
}
