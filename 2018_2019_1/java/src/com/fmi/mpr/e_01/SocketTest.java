package com.fmi.mpr.e_01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.stream.IntStream;

public class SocketTest {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8888);
        System.out.println("Server is running");
        
        while (true) {
        	Socket client = server.accept();
	        InputStream in = client.getInputStream();
	        byte[] buffer = new byte[4096];
	        int bytesRead = 0;
	        try {
		        while((bytesRead = in.read(buffer, 0, 4096)) > 0) {
		        	System.out.println(new String(buffer, 0, bytesRead));
		        }
	        } catch (SocketException se) {
	        	System.out.println(se.getMessage());
	        }
        }
    }
}
