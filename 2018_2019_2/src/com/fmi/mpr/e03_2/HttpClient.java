package com.fmi.mpr.e03_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

import com.fmi.mpr.e01_2.ServerUtils;

public abstract class HttpClient implements Runnable {
	
	protected Socket clientSocket;
	protected Request req;
	protected Response res;
	
	public HttpClient(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			this.req = receiveRequest();
			this.res = buildResponse();
			sendResponse(res);
		} catch (Exception e) {
			e.printStackTrace(); // TODO
		} finally {
			ServerUtils.close(clientSocket);
		}
	}
	
	public InputStream getInputStream() throws IOException {
		return this.clientSocket.getInputStream();
	}
	
	public OutputStream getOutputStream() throws IOException {
		return this.clientSocket.getOutputStream();
	}
	
	public String getStringFromStackTrace(Throwable e) {
		
		StringBuilder msg = new StringBuilder();
		
		if (e != null) {
			for (StackTraceElement element : e.getStackTrace()) {
				msg.append(element.toString()).append("\r\n");
			}
			
			String cause = getStringFromStackTrace(e.getCause());
			msg.append(cause);
		}
		
		return msg.toString();
	}
	
	abstract Response buildResponse() throws IOException;
	abstract Request receiveRequest() throws IOException;
	abstract void sendResponse(Response res) throws IOException;
}
