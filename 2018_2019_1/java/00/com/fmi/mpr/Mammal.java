package com.fmi.mpr;

public interface Mammal extends Animal {
	default void jump() {
		System.out.println("Skacham");
	}
}
