package com.fmi.mpr.e03_2;

public enum HttpVersion {
	HTTP1_0("HTTP/1.0"),
	HTTP1_1("HTTP/1.1"),
	HTTP2_0("HTTP/2.0");
	
	private String value;
	
	HttpVersion(String value) {
		this.value = value;
	}
	
	public String getVersion() {
		return this.value;
	}
	
	public static HttpVersion getByString(String value) {
		
		for (HttpVersion version : values()) {
			if (version.value.equals(value)) {
				return version;
			}
		}
		
		throw new IllegalArgumentException("Invalid HTTP version " + value);
	}
}
