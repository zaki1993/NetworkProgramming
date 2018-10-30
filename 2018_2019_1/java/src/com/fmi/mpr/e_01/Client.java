package com.fmi.mpr.e_01;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid parameters specified..!");
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        Client c = new Client();
        c.connect(host, port);
    }

    private Socket client;

    public Client() {
        this.client = new Socket();
    }

    public void connect(String host, int port) throws IOException {

        SocketAddress address = new InetSocketAddress(host, port);
        client.connect(address);
        try {
            doAction(client);
        } finally {
            Utils.close(client);
        }
    }

    private void doAction(Socket client) throws IOException {

        if (client != null) {
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            processInput(out);
            processOutput(in);
        }
    }

    private void processOutput(InputStream in) throws IOException {

        DataInputStream serverIn = new DataInputStream(in);

        long result = serverIn.readLong();
        if (result == -1L) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 4096)) > -1) {
                System.out.print(new String(buffer,0 , bytesRead));
            }
        } else {
            System.out.println("The result is " + result);
        }
    }

    private void processInput(OutputStream out) throws IOException {

        try (Scanner sc = new Scanner(System.in)) {

            DataOutputStream serverOut = new DataOutputStream(out);

            System.out.print("a=");
            long a = sc.nextLong();
            System.out.print("b=");
            long b = sc.nextLong();
            System.out.print("operation=");
            String operation = sc.next();

            serverOut.writeLong(a);
            serverOut.writeLong(b);
            serverOut.writeChars(operation);
            	
            serverOut.flush();
        }
    }
}
