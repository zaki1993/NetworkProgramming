package com.fmi.mpr.e00;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Streams {
	
	
	public static void main(String[] args) throws IOException {
		
		writeToFile("mpr.txt", "test123");
		readFile("mpr.txt");
		
	}
	
	public static void readFile(String fileName) throws IOException {
		
		File f = new File(fileName);
		
		if (!f.exists()) {
			f.createNewFile();
		}
		
		try (InputStream in = new FileInputStream(f)) {
			
			byte[] buffer = new byte[4096];
			
			int bytesRead = 0;
			while ((bytesRead = in.read(buffer, 0, 4096)) > 0) {
				System.out.println(new String(buffer, 0, bytesRead));
			}
		}
	}
	
	public static void writeToFile(String fileName, String content) throws IOException {
		
		File f = new File(fileName);
		
		if (!f.exists()) {
			f.createNewFile();
		}
		
		try (PrintWriter pw = new PrintWriter(f)) {
			pw.println(content);
		}
	}
}
