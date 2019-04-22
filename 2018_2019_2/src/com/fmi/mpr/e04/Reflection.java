package com.fmi.mpr.e04;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {
	
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {

		Class clazz = Class.forName("com.fmi.mpr.e04.Test");
		Method m = clazz.getDeclaredMethod("test", null);
		m.setAccessible(true);
		
		Constructor c = clazz.getDeclaredConstructor(int.class);
		
		c.setAccessible(true);
		m.invoke(c.newInstance(5));
	}
}
