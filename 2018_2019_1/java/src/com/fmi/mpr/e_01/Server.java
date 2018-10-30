package com.fmi.mpr.e_01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    private ServerSocket listener;
    private boolean running;

    public Server(int port) throws IOException {
        this.listener = new ServerSocket(port);
    }

    public void start() throws IOException {

        if (!running) {
            running = true;
            System.out.println("Server started running on port " + this.listener.getLocalPort());
            while (running) {
                try {
                    listen();
                } catch (IOException e) {
                    stop();
                }
            }
        }
    }

    private void stop() throws IOException {

        if (running) {
            running = false;
            Utils.close(listener);
        }
    }

    private void listen() throws IOException {

        try (Socket client = this.listener.accept()) {

            System.out.println("Client connected..!");

            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            long result = -1L;
            try {
                result = processInput(in);
                processOutput(out, result, null);
            } catch (UnsupportedOperationException e) {
                processOutput(out, result, e);
            }
        } finally {
            System.out.println("Connection with client closed!");
        }
    }

    private void processOutput(OutputStream out, long result, Exception e) {

        System.out.println("Processing output..!");

        try {
            DataOutputStream dataOut = new DataOutputStream(out);

            if (e != null) {
                dataOut.writeLong(-1L);
                dataOut.write(e.getMessage().getBytes());
            } else {
                dataOut.writeLong(result);
            }
            dataOut.flush();
        } catch (Exception ex) {
            System.out.println("Exception while processing client output: " + ex.getMessage());
        }
    }

    private long processInput(InputStream in) {

        System.out.println("Processing input..!");

        long result = -1L;

        try {
            DataInputStream dataIn = new DataInputStream(in);
            long a = dataIn.readLong();
            long b = dataIn.readLong();
            char operation = dataIn.readChar();

            result = doOperation(a, b, operation);
        } catch (UnsupportedOperationException uoe) {
            throw uoe;
        } catch (Exception e) {
            System.out.println("Exception while processing client input: " + e.getMessage());
        }

        return result;
    }

    private long doOperation(long a, long b, char operation) {

        long result;

        try {
            switch (operation) {
                case '+':
                    result = a + b;
                    break;
                case '-':
                    result = a - b;
                    break;
                case '*':
                    result = a * b;
                    break;
                case '/':
                    result = a / b;
                    break;
                case '%':
                    result = a % b;
                    break;
                case '^':
                    result = (long) Math.pow(a, b);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported operation " + operation);
            }
        } catch (ArithmeticException ae) {
            throw new UnsupportedOperationException(ae.getMessage());
        }

        return result;
    }
}
