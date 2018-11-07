package com.fmi.mpr.e_01.p_02;

import java.io.IOException;

public class Demo {
	
	public static void main(String[] args) throws IOException {
		
		Server s = new Server(5555);
		s.start();
	}
}
