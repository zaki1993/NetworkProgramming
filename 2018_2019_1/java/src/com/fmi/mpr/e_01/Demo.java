package com.fmi.mpr.e_01;

import java.io.IOException;

public class Demo {
    public static void main(String[] args) throws IOException {

        Server s = new Server(8888);
        s.start();
    }
}
