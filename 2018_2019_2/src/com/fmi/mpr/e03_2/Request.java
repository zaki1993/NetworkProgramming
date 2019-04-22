package com.fmi.mpr.e03_2;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
	
	private final RequestMethod method;
	private final HttpVersion httpProtocolVersion;
	private final String uri;
	private final Map headers;
	
	public Request(RequestMethod method, String uri, HttpVersion httpProtocolVersion) {
		this.method = method;
		this.uri = uri;
		this.httpProtocolVersion = httpProtocolVersion;
		this.headers = new LinkedHashMap<>();
	}
	
	public void addHeader(String key, String value) {
		
		if (this.headers != null) {
			this.headers.put(key, value);
		}
	}
	
	public RequestMethod getMethod() {
		return this.method;
	}
	
	public String getUri() {
		return this.uri;
	}
	
	public Map getHeaders() {
		return Collections.unmodifiableMap(this.headers);
	}
}
