package com.fmi.mpr.e_01;

import java.io.Closeable;
import java.io.IOException;

public final class Utils {

    public static void close(Closeable c) throws IOException {

        if (c != null) {
            c.close();
        }
    }
}
