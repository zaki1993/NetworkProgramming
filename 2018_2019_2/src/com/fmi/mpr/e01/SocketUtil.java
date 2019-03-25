package com.fmi.mpr.e01;

import java.io.Closeable;
import java.io.IOException;

public final class SocketUtil {
	
	public static final int BUFFER_SIZE = 4096;
	
	public static void close(Closeable c) {
		
		try {
			if (c != null) {
				c.close();
			}
		} catch (IOException e) {
			// TODO log message
		}
	}
	
}
