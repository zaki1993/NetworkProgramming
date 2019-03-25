package com.fmi.mpr.e01_2;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public final class ServerUtils {
	
	public static final int BUFFER_SIZE = getOptimalBufferSize();
	
	public static void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				noop();
			}
		}
	}
	
	public static void noop() {
		// do nothing
	}
	
	private static int getOptimalBufferSize() {
		BlockSizeFinder finder = new BlockSizeFinder(System.out);
		
		int optimalBlockSize = finder.getBlockSize();
		System.out.println("Optimal block size is " + optimalBlockSize);
		
		return optimalBlockSize;
	}
	
	private static class BlockSizeFinder extends BufferedOutputStream {

	        public BlockSizeFinder(OutputStream out) {
	            super(out);
	        }

	        public int getBlockSize() {
	            return buf.length;
	        }
	}
}
