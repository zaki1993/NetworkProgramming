package com.fmi.mpr;

public abstract class Cat implements Mammal {
	
	private Mammal child;
	
	protected Cat(Mammal child) {
		this.child = child;
		System.out.println(this.hashCode());
	}

	@Override
	public void jump() {
		System.out.println("Kotkata skacha");
	}
	
	@Override
	public void speak() {
		System.out.println("I am a cat");
	}
	
	public Mammal getChild() {
		return this.child;
	}
	
	public abstract void meow();
}
