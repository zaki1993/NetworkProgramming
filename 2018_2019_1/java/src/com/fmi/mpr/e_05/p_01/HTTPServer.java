package com.fmi.mpr.e_05.p_01;

import java.net.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.io.*;

public class HTTPServer {
	
	private ServerSocket ss;
	
	public HTTPServer() throws IOException {
		this.ss = new ServerSocket(8888);
	}
	
	public void run() throws IOException {
		
		while (true) {
			try {
				Socket client = ss.accept();
				processClient(client);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processClient(Socket client) throws IOException {
		
		try (BufferedInputStream br = new BufferedInputStream(client.getInputStream());
			 PrintStream ps = new PrintStream(client.getOutputStream(), true)) {
			
			String response = read(br);
			write(ps, response);		
		}
		
	}

	private void write(PrintStream ps, String response) {
		
		if (ps != null) {
			ps.println("HTTP/1.0 200 OK");
			ps.println();
			ps.println("<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"<head>\n" + 
					"	<title></title>\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"<h1>Hello</h1>" + 
					"<form method=\"POST\" action=\"/\">" +
						"<input type=\"text\" name=\"a\"/>" +
						"<input type=\"text\" name=\"b\"/>" +
						"<input type=\"text\" name=\"oper\"/>" +
						"<input type=\"submit\" value=\"Send\">" +
					"</form>" +
					"<h2>" + (response == null || response.trim().isEmpty() ? "" : response) + "</h2>" +
					"</body>\n" + 
					"</html>");
		}
	}

	private String read(BufferedInputStream bis) throws IOException {
		
		if (bis != null) {
			StringBuilder request = new StringBuilder();
			
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			
			while ((bytesRead = bis.read(buffer, 0, 1024)) > 0) {
				request.append(new String(buffer, 0, bytesRead));
				
				if (bytesRead < 1024) {
					break;
				}
			}
			
			return parseRequest(request.toString());
		}
		return "Error";
	}
	
	private String parseRequest(String request) {
		
		System.out.println(request);

		String[] lines = request.split("\n");
		
		StringBuilder body = new StringBuilder();
		boolean readBody = false;
		for (String line : lines) {
			if (readBody) {
				body.append(line);
			}
			if (line.trim().isEmpty()) {
				readBody = true;
			}
		}
		
		return parseBody(body.toString());
	}

	private String parseBody(String body) {
		
		if (body != null && !body.trim().isEmpty()) {
			String[] operands = body.split("&");
			int a = Integer.parseInt(operands[0].split("=")[1]);
			int b = Integer.parseInt(operands[1].split("=")[1]);
			String operation = operands[2].split("=")[1];
			
			return operate(a, b, operation);
		}
		return null;
	}

	private String operate(int a, int b, String operation) {
		
		System.out.println(a + b + " " + operation);
		switch (operation) {
			case "+": return String.valueOf(a + b); 
			case "*": return String.valueOf(a * b); 
			default:
				return String.valueOf(a - b);
		}
	}

	public static void main(String[] args) throws IOException {
		
		HTTPServer server = new HTTPServer();
		server.run();
	}
}
