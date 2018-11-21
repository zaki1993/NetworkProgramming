package com.fmi.mpr.e_05.p_01;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class HttpClient {
	
	private Socket socket;
	private StringBuilder request;
	private String fileName;
	
	public HttpClient(Socket socket) {
		this.socket = socket;
		request = new StringBuilder();
	}
	
	public void receive() throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		String line = null;
		while ((line = reader.readLine()) != null) {
			request.append(line);
			if (line.trim().isEmpty()) {
				break;
			}
		}
		parseRequest();
	}
	
	private void parseRequest() {
		
		String[] lines = request.toString().split("\n");
		String header = lines[0];
		
		String[] headerParts = header.split(" ");
		this.fileName = headerParts[1].substring(1);		
	}

	public void send() throws IOException {
		
		PrintStream ps = new PrintStream(socket.getOutputStream(), true);
		ps.println("HTTP/1.1 200 OK");
		ps.println();
		try {
			sendKotka(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			/*ps.println("<!DOCTYPE html>\n" + 
					   "<html>\n" + 
					   "<head>\n" + 
					   "	<title></title>\n" + 
					   "</head>\n" + 
					   "<body>\n" + 
					   "<h1>Error: " + e.getMessage() + "</h1>" +
					   "</body>\n" + 
					   "</html>");*/
			ps.println("<!DOCTYPE html>\n" + 
					   "<html>\n" + 
					   "<head>\n" + 
					   "	<title></title>\n" + 
					   "</head>\n" + 
					   "<body>\n" + 
					   "<form action=\"/action_page.php\">\n" + 
					   "			  <input type=\"file\" name=\"pic\" accept=\"image/*\">\n" + 
					   "			  <input type=\"submit\">\n" + 
					   "			</form> " +
					   "</body>\n" + 
					   "</html>");
		}
	}

	private void sendKotka(BufferedOutputStream bos) throws IOException {
				
		File f = new File("/home/zaki/Desktop/git/NetworkProgramming/2018_2019_1/java/src/" + fileName);
		FileInputStream fileIn = new FileInputStream(f);
		byte[] buffer = new byte[4096];
		
		int bytesRead = 0;
		while ((bytesRead = fileIn.read(buffer, 0, 4096)) > 0) {
			bos.write(buffer, 0, bytesRead);
		}
		bos.flush();
	}

}
