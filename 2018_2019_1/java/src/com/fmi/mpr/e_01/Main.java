package com.fmi.mpr.e_01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Socket s = new Socket();
        SocketAddress address = new InetSocketAddress("localhost", 5555);
        //s.bind(address);
        s.connect(address);
        
        s.getOutputStream();
        s.getInputStream();
        
        
        
    }
}
