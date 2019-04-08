package com.fmi.mpr.e03;

import java.net.Socket;
import java.util.Map;
import java.io.*;

public class URLReader {
	
	private InputStream in;
	private OutputStream out;
	
	private Request request;
	
	public URLReader(Socket socket) throws IOException {
		this.in = socket.getInputStream();
		this.out = socket.getOutputStream();
	}

	public void process() {
		
		try {
			readRequest();
			
			if (request != null) {
				renderFile(request.getURI());
			}
			//printRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void renderFile(String uri) throws IOException {
		
		String resourcesUri = "E:\\Programming\\Eclipse\\MPR\\src\\resources\\" + uri;
		File file = new File(resourcesUri);
		System.out.println(file.getAbsolutePath());
		if (file.exists()) {
			try (FileInputStream fileIn = new FileInputStream(file)) {
				byte[] buffer = new byte[4096];
				int bytesRead = 0;
				PrintWriter pw = new PrintWriter(out, true);
				pw.println(request.getHttpVersion() + " 200 OK");
				pw.println("Host: " + request.getHeaders().get("Host"));
				if (uri.endsWith(".mp4")) {
					pw.println("Content-Type: video/mp4");
				} else if (uri.endsWith(".jpg")) {
					pw.println("Content-Type: image/jpeg");
				} else {
					pw.println("Content-Type: text/html");
				}
				pw.println();
				while ((bytesRead = fileIn.read(buffer, 0, 4096)) > -1) {
					out.write(buffer, 0, bytesRead);
				}
				out.flush();
			}
		} else {
			renderError("Resource " + uri + " not found..!");
		}
	}

	private void renderError(String errorMsg) {
		System.out.println("Rendering error");
		PrintWriter pw = new PrintWriter(out, true);
		pw.println(request.getHttpVersion() + " 404 Not Found");
		pw.println("Content-Type: text/html");
		pw.println("Host: " + request.getHeaders().get("Host"));
		pw.println();
		pw.println(errorMsg);
	}

	private void printRequest() {
		System.out.println(request.getMethod());
		System.out.println(request.getURI());
		Map<String, String> headers = request.getHeaders();
		
		for (Map.Entry<String, String> header : headers.entrySet()) {
			System.out.println(header.getKey() + ": " + header.getValue());
		}
	}

	private void readRequest() throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		String baseLine = reader.readLine();
		
		if (baseLine != null) {
			String[] baseLineParts = baseLine.split(" ");
			if (baseLineParts != null && baseLineParts.length == 3) {
				String method = baseLineParts[0];
				String URI = baseLineParts[1];
				String protocolVersion = baseLineParts[2]; 
				this.request = new Request(method, URI, protocolVersion);
			}
		}
		
		String header = null;
		while ((header = reader.readLine()) != null) {
			int delimeterIdx = header.indexOf(":");
			if (delimeterIdx != -1) {
				String key = header.substring(0, delimeterIdx).trim();
				String value = header.substring(delimeterIdx + 1).trim();
				request.addHeader(key, value);
			}
			if (header.trim().isEmpty()) {
				break;
			}
		}
	}

}
