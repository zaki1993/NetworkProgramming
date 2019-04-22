package com.fmi.mpr.e03_2;

public enum HttpStatus {
	OK(200, "OK"),
	NOT_FOUND(404, "Not Found");
	
	private int status;
	private String strValue;
	
	private HttpStatus(int status, String strValue) {
		this.status = status;
		this.strValue = strValue;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public String getStrValue() {
		return this.strValue;
	}	
}
