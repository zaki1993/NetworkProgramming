package com.fmi.mpr.e00;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.fmi.mpr.Person;

public class Generics {
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		/*
		list.add(e)
		list.addAll(c)
		list.add(index, element);
		list.get(index)
		list.contains(o)*/
		
		/*
		 * List<Car> list1 = new LinkedList<>();
		 * 
		 * Set<Integer> set = new TreeSet<>((i1, i2) -> i1 - i2);
		 * 
		 * set.add(2); set.add(-1); set.add(1); set.add(9); set.add(-2);
		 * 
		 * System.out.println(set);
		 */
		
		//test(new WhiteCate("gosho"));
		
		Cat c = new WhiteCate("test");
		Human h = new Person();
		
		System.out.println(Animal.class.isAssignableFrom(c.getClass()));
		
		if (c instanceof Animal) {
			((Animal) h).talk();
		}
	}
	
	public static <T extends Animal> void test(T element) {
		
	}
}
