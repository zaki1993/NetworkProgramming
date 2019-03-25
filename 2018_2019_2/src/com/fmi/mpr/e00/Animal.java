package com.fmi.mpr.e00;

public interface Animal extends Creature {
	
	int x = 5;
	
	void eat();
	
	void talk();
	
	default void walk() {
		
	}
}
