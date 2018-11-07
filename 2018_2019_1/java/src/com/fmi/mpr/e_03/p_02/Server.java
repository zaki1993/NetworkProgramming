package com.fmi.mpr.e_03.p_02;
import java.net.*;
import java.util.Arrays;
import java.io.*;

public class Server {
	
	private ServerSocket listener;
	
	public Server() throws IOException {
		
		listener = new ServerSocket(8888);
		
		Socket s = listener.accept();
		
		BufferedInputStream br = new BufferedInputStream(s.getInputStream());
		
		byte[] buffer = new byte[4096];
		int bytesRead = 0;
		
		boolean nameFound = false;
		
		File image = null;
		FileOutputStream imageOut = null;
		
		while ((bytesRead = br.read(buffer, 0, 4096)) > 0) {
			
			int newLineIdx = getCarriageIdx(buffer, bytesRead, new byte[] {13, 10});
			if (!nameFound && newLineIdx != -1) {
				nameFound = true;
				String fileName = getNameFromBytes(buffer, newLineIdx).trim();
				byte[] subArray = getSubArray(buffer, newLineIdx, bytesRead);
				image = new File("../" + fileName);
				System.out.println("File name is: " + fileName);
				image.createNewFile();
				imageOut = new FileOutputStream(image);
				//System.out.println(buffer.length + " " + subArray.length);
				imageOut.write(subArray);
			} else {
				//System.out.println(Arrays.toString(buffer));
				System.out.println((imageOut == null) + " " + nameFound);
				if (nameFound && imageOut != null) {
					imageOut.write(buffer, 0, bytesRead);
				}
			}
		}
		if (imageOut != null) {
			imageOut.flush();
		}		
	}
	
	private static byte[] getSubArray(byte[] buffer, int carriageIdx, int bytesRead) {
		
		byte[] result = new byte[bytesRead - carriageIdx];
		
		for (int i = carriageIdx; i < bytesRead; i++) {
			result[i - carriageIdx] = buffer[i];
		}
		
		return result;
	}
	
	private String getNameFromBytes(byte[] buffer, int newLineIdx) {
		
		return new String(buffer, 0, newLineIdx);
	}

	private static int getCarriageIdx(byte[] buffer, int bytesRead, byte[] carriage) {
        
		byte[] carriageReturn = carriage;
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
                    result = i + carriage.length;
                    break;
                }
            }
        }
        
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		Server s = new Server();
	}
}
