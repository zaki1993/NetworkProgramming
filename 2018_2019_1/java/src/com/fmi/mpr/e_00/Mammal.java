package com.fmi.mpr.e_00;

public interface Mammal extends Animal {
	default void jump() {
		System.out.println("Skacham");
	}
}
