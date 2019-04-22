package com.fmi.mpr.e03_2;

public class Response {
	
	private final HttpVersion httpProtocolVersion;
	private final HttpStatus status;
	
	private String responseMsg;
	
	public Response(HttpVersion httpProtocolVersion, HttpStatus status) {
		this.httpProtocolVersion = httpProtocolVersion;
		this.status = status;
	}
	
	public String getVersion() {
		return this.httpProtocolVersion.getVersion();
	}
	
	public int getStatus() {
		return this.status.getStatus();
	}
	
	public String getStatusAsString() {
		return this.status.getStrValue();
	}
	
	public String getResponseMsg() {
		return this.responseMsg;
	}
	
	public void setResponseMsg(String msg) {
		this.responseMsg = msg;
	}
}
