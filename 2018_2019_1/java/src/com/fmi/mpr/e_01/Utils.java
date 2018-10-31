package com.fmi.mpr.e_01;

import java.io.Closeable;
import java.io.IOException;

public final class Utils {

    public static void close(Closeable c) {

        if (c != null) {
        	try {
        		c.close();
        	} catch (IOException e) {
        		// Ignored
        	}
        }
    }
}
