package com.fmi.mpr.e02_2;

import java.io.Serializable;

public class Person implements Serializable {
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
