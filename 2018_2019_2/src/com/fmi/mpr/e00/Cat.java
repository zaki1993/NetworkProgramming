package com.fmi.mpr.e00;

public abstract class Cat implements Animal {
	
	private String name;
	
	public Cat(String name) {
		this.name = name;
	}
	
	@Override
	public void eat() {
		
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
