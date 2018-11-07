package com.fmi.mpr.e_03.p_02;

import java.net.*;
import java.util.Arrays;
import java.io.*;

public class Main {
	
	private static final byte[] CARRIAGE_RETURN = new byte[] { 13, 10, 13, 10 };
	
	public static void main(String[] args) throws IOException {
		
		Socket s = new Socket();
		
		SocketAddress googleAddress = new InetSocketAddress("i49.vbox7.com", 80);
		
		s.connect(googleAddress);
		
		System.out.println("Connected to google");
		
		PrintStream ps = new PrintStream(s.getOutputStream(), true);
		ps.println("GET /o/dab/dabbbe370.jpg HTTP/1.0\r\n");
		ps.println("Host: i49.vbox7.com\r\n");
		
		File google = new File("kotka.png");
		FileOutputStream googleOut = new FileOutputStream(google);
		
		InputStream in = s.getInputStream();
		
		byte[] buffer = new byte[4096];
		int bytesRead = 0;
		
		boolean carriageFound = false;
		
		while ((bytesRead = in.read(buffer, 0, 4096)) > 0) {
			
			int carriageIdx = getCarriageIdx(buffer, bytesRead);
			//System.out.println(carriageIdx);
			
			if (carriageIdx != -1) {
				byte[] withoutCarriage = getSubArray(buffer, carriageIdx, bytesRead);
				googleOut.write(withoutCarriage);
				carriageFound = true;
			} else {
				if (carriageFound) {
					googleOut.write(buffer, 0, bytesRead);
				}
			}
		}
		
		googleOut.flush();
		
	}

	private static byte[] getSubArray(byte[] buffer, int carriageIdx, int bytesRead) {
		
		byte[] result = new byte[bytesRead - carriageIdx];
		
		for (int i = carriageIdx; i < bytesRead; i++) {
			result[i - carriageIdx] = buffer[i];
		}
		
		return result;
	}

	private static int getCarriageIdx(byte[] buffer, int bytesRead) {
        
		byte[] carriageReturn = CARRIAGE_RETURN;
        int result = -1;
        
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] == carriageReturn[0]) {
                boolean hasCarriage = true;
                for (int j = 1; j < carriageReturn.length; j++) {
                    if (buffer[i + j] != carriageReturn[j]) {
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
