package com.fmi.mpr.e03_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;

public class HttpCalculatorClient extends HttpClient {

	public HttpCalculatorClient(Socket clientSocket) {
		super(clientSocket);
	}

	@Override
	Request receiveRequest() throws IOException {

		Request request = null;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream()));
		
		// Read first line of the request
		String baseLine = reader.readLine();
		
		if (baseLine != null) {
			String[] baseLineParts = baseLine.split(" ");
			if (baseLineParts != null && baseLineParts.length == 3) {
				RequestMethod method = RequestMethod.valueOf(baseLineParts[0]);
				String uri = baseLineParts[1];
				HttpVersion httpProtocolVersion = HttpVersion.getByString(baseLineParts[2]);
				request = new Request(method, uri, httpProtocolVersion);
			}
		} 
		
		// read headers
		if (request != null) {
			String header = null;
			while ((header = reader.readLine()) != null) {
				if (!header.trim().isEmpty()) {
					int delimeterIdx = header.indexOf(": ");
					String headerName = header.substring(0, delimeterIdx);
					String headerValue = header.substring(delimeterIdx + 1);
					request.addHeader(headerName, headerValue);
				} else {
					break;
				}
			}
		}
		
		if (request != null) {
			if (request.getMethod() == RequestMethod.POST) {
				// todo read body
			}
		}
		
		return request;
	}

	@Override
	void sendResponse(Response res) throws IOException {
		
		PrintWriter pw = new PrintWriter(getOutputStream(), true);
		pw.println(res.getVersion() + " " + res.getStatus() + " " + res.getStatusAsString());
		pw.println("Host: " + req.getHeaders().get("Host"));
		pw.println();
		pw.println(res.getResponseMsg());
	}

	@Override
	Response buildResponse() throws IOException {
		
		Response response = null; // new Response(HttpVersion.HTTP1_1, HttpStatus.OK);
		// /calculate?x=5&y=8&operation=mutliply
		String uri = req.getUri();
		
		try {
			if (req.getMethod() == RequestMethod.GET) {
				if (uri.startsWith("/calculate")) {
					response = new Response(HttpVersion.HTTP1_1, HttpStatus.OK);
					int queryIdx = uri.indexOf("?");
					String query = uri.substring(queryIdx + 1);
					BigDecimal answer = calculateAnswer(query);
					response.setResponseMsg("The answer is: " + answer);
				} else {
					response = new Response(HttpVersion.HTTP1_1, HttpStatus.NOT_FOUND);
					response.setResponseMsg("Unsupported operation..!");
				}
			} else {
				response = new Response(HttpVersion.HTTP1_1, HttpStatus.NOT_FOUND);
				response.setResponseMsg("Unsupported operation..!");
			}
		} catch (Exception e) {
			response = new Response(HttpVersion.HTTP1_1, HttpStatus.NOT_FOUND);
			response.setResponseMsg(getStringFromStackTrace(e));
		}
		
		return response;
	}

	private BigDecimal calculateAnswer(String query) {

		String[] queryParts = query.split("&");
		
		String[] operand1Parts = queryParts[0].split("=");
		BigDecimal operand1Value = new BigDecimal(operand1Parts[1]);
		
		String[] operand2Parts = queryParts[1].split("=");
		BigDecimal operand2Value = new BigDecimal(operand2Parts[1]);
		
		String[] operationParts = queryParts[2].split("=");
		String operationName = operationParts[1];
		
		BigDecimal result;
		switch (operationName) {
			case "multiply": 
				result = operand1Value.multiply(operand2Value);
				break;
			default: 
				result = operand1Value.add(operand2Value);
		}
		
		return result;
	}

}
