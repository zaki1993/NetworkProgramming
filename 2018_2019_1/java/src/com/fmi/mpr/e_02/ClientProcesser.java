package com.fmi.mpr.e_02.p_01;

import java.net.*;

import com.fmi.mpr.e_01.Utils;

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
			Utils.close(s);
		}
	}
}
