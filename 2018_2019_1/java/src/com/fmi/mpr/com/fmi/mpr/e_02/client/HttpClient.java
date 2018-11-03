package com.fmi.mpr.com.fmi.mpr.e_02.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class HttpClient {

    private static final String IMG_REGEX = "<img height.*?>";
    private static final String SRC_REGEX = "src=\".*?\"";

    private Socket client;

    public HttpClient() {
        this.client = new Socket();
    }

    public void connect(String host, int port) throws IOException {

        SocketAddress address = new InetSocketAddress(host, port);
        this.client.connect(address);
    }

    public void transferData() throws IOException {

        PrintWriter ps = new PrintWriter(client.getOutputStream(), true);

        ps.println("GET /search?q=dog&tbm=isch HTTP/1.1\r\n");
        ps.println("Host: google.com\r\n");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            String content = readResponse(in);
            readImages(content);
        }
    }

    public static void main(String[] args) throws IOException {

        HttpClient c = new HttpClient();
        c.connect("google.com", 80);
        c.transferData();
    }

    private String readResponse(BufferedReader in) throws IOException {

        File out = new File("output.html");

        try (FileOutputStream fileOut = new FileOutputStream(out)) {

            boolean write = false;
            String line = null;

            StringBuilder builder = new StringBuilder();
            while ((line = in.readLine()) != null) {
                if (line.startsWith("<!doctype html>")) {
                    write = true;
                }

                if (write) {
                    fileOut.write(line.getBytes());
                    builder.append(line);
                }

                if (line.endsWith("</html>")) {
                    break;
                }
            }

            return builder.toString();
        }
    }

    private void readImages(String content) throws IOException {

        Pattern pattern = Pattern.compile(IMG_REGEX);
        Matcher matcher = pattern.matcher(content);
        int count = 0;
        while (matcher.find()) {
            ;
            String line = matcher.group(0);
            String url = extractSrcFromImgTag(line);
            getUrlData(url, "img/test_" + count++ + ".png");
        }
    }

    private String extractSrcFromImgTag(String line) {

        String result = null;

        Pattern pattern = Pattern.compile(SRC_REGEX);
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            ;
            String url = matcher.group(0);
            result = url.substring(url.indexOf("\"") + 1, url.lastIndexOf("\""));
        }

        return result;
    }

    private void getUrlData(String url, String fileName) throws IOException {

        System.out.println("Processing " + url);

        String host = extractHost(url);
        String query = extractQuery(url, host);
        System.out.println("Host is " + host);
        System.out.println("Query is " + query);
        System.out.println("Processing request\n");

        SocketAddress address = new InetSocketAddress(host, 80);
        Socket s = new Socket();
        s.connect(address);

        File f = new File(fileName);
        f.createNewFile();
        try (PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
             FileOutputStream fileOut = new FileOutputStream(f);
             InputStream in = s.getInputStream()) {

            // write headers
            pw.println("GET " + query + " HTTP/1.0\r\n");
            pw.println("Host: " + host + "\r\n");

            // read bytes
            byte[] bytes = in.readAllBytes();
            int carriageIdx = Util.findCarriageReturnIndex(bytes);

            byte[] imageBytes = new byte[bytes.length - carriageIdx];
            IntStream.range(carriageIdx, bytes.length).forEach(i -> imageBytes[i - carriageIdx] = bytes[i]);

            fileOut.write(imageBytes);
            fileOut.flush();
        }
    }

    private String extractHost(String url) {
        int start = url.indexOf("//") + 2;
        int end = url.substring(start).indexOf("/");
        return url.substring(start, start + end);
    }

    private String extractQuery(String url, String host) {

        int start = url.indexOf(host) + host.length();
        return url.substring(start);
    }
}
