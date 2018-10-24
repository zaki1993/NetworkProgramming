package com.fmi.mpr;

public class HouseCat extends Cat {
	
	private String name;
	
	public HouseCat(String name) {
		this(name, null);
	}
	
	public HouseCat(String name, Mammal child) {
		super(child);
		this.name = name;
		System.out.println(this.hashCode());
	}
	
	@Override
	public void walk() {
		System.out.println("House cat is walking!");
	}
	
	@Override
	public void speak() {
		System.out.println("I am " + name);
	}

	@Override
	public void meow() {
		System.out.println("meoww meowww");
	}
	
	public void doHouseStuff() {
		System.out.println("Break glasses");
	}
}
