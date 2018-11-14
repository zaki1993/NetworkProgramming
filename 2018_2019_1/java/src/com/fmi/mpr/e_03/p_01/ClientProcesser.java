package com.fmi.mpr.e_03.p_01;

import java.net.*;

import java.io.*;

public class ClientProcesser extends Thread {
	
	private InputStream in ;
	private Socket s;
	
	public ClientProcesser(Socket s) throws IOException {
		this.in = s.getInputStream();
		this.s = s;
	}

	@Override
	public void run() {
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {;}
			}
		}
	}
}
