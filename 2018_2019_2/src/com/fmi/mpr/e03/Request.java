package com.fmi.mpr.e03;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Request {
	
	private enum RequestMethod {
		GET, POST, PUT, DELETE
	}
	
	private final RequestMethod method;
	private final String URI;
	private final String httpVersion;
	private final Map headers;
	
	public Request(String method, String URI, String httpVersion) {
		this.method = RequestMethod.valueOf(method);
		this.URI = URI;
		this.httpVersion = httpVersion;
		this.headers = new HashMap<>();
	}
	
	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public String getMethod() {
		return method.toString();
	}

	public String getURI() {
		return URI;
	}
	
	public String getHttpVersion() {
		return httpVersion;
	}

	public Map getHeaders() {
		return Collections.unmodifiableMap(this.headers);
	}
}
