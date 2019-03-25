package com.fmi.mpr.e00;

public class Car {
	
	private static int count = 0;
	
	private Engine e;
	
	public Car() {
		this.e = new Engine();
	}
	
	private class Engine {
		
		private int hp;
	}
	
	public void printInfo() {
		System.out.println(e.hp);
		System.out.println(Car.count);
	}
}
