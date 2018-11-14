package com.fmi.mpr.e_04.p_02.client;

import java.io.Serializable;

public class Person implements Serializable {
	
	private static int count = 0;
	
	private final String firstName;
	private final int age;
	private final int number;
	
	private final String lastName;
	
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = 5;
		this.number = Person.count++;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}	
	
	public int getAge() {
		return age;
	}
	
	public int getNumber() {
		return number;
	}
}
