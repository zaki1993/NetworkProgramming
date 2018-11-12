package com.fmi.mpr.e_03.p_01.external;

import java.net.*;

import java.io.*;

import java.util.*;
import java.util.stream.IntStream;

public class URLImageDownloader {
	
	private static final byte[] carriageReturn = new byte[] { 13, 10, 13, 10 };
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		try (Socket s = new Socket()) {
			
			SocketAddress sa = new InetSocketAddress("encrypted-tbn0.gstatic.com", 80);
			s.connect(sa);
			
			PrintStream ps = new PrintStream(s.getOutputStream(), true);
			ps.println("GET /images?q=tbn:ANd9GcRVSLvxWEqf7bhF_iTaluSV5YN7xQI0ZKmh1ltuArzlETk8G8cs HTTP/1.0\r\n");
			ps.println("Host: encrypted-tbn0.gstatic.com\r\n");
			
			InputStream in = s.getInputStream();
				
			File doge = new File("doge.png");
			FileOutputStream dogeOut = new FileOutputStream(doge);
			
			byte[] buffer = new byte[4096];
			int bytesRead = 0;
			
			boolean carriageFound = false;
			
			while ((bytesRead = in.read(buffer, 0, 4096)) > 0) {
				
				int idx = findCarriageReturnIndex(buffer);
				if (!carriageFound && idx != -1) {
					carriageFound = true;
					
					byte[] second = getByteSubArray(buffer, idx, bytesRead);
					dogeOut.write(second);
					continue;	
				}
				dogeOut.write(buffer, 0, bytesRead);
				
			}
			dogeOut.flush();
		}
	}
	
	private static byte[] getByteSubArray(byte[] buffer, int idx, int bytesRead) {
		
		byte[] result = new byte[bytesRead - idx];
		
		for (int i = idx; i < bytesRead; i++) {
			result[i - idx] = buffer[i];
		}
		
		return result;
	}

	public static int findCarriageReturnIndex(final byte[] requestBytes) {

        int result = -1;
        for (int i = 0; i < requestBytes.length; i++) {
            if (requestBytes[i] == carriageReturn[0]) {
                boolean hasCarriage = true;
                for (int j = 1; j < carriageReturn.length; j++) {
                    if (requestBytes[i + j] != carriageReturn[j]) {
                        hasCarriage = false;
                    }
                }
                if (hasCarriage) {
                    result = i + 4;
                    break;
                }
            }
        }
        return result;
	}
}
