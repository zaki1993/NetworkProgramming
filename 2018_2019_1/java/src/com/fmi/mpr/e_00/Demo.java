package com.fmi.mpr.e_00;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.fmi.mpr.*;

public class Demo {
	public static void main(String[] args) {
		Cat child = new HouseCat("Gosho");
		Cat hc = new HouseCat("Pesho", child);
		hc.speak();
		hc.getChild().speak();
		hc.jump();

		// подаваме компаратор като параметър на конструктура.
		// предварително създаваме клас, който имплементира интерфейса Comparator
		Set<Name> namesComparator = new TreeSet<>(new NameComparator());

		// не подаваме нищо на конструктура, но NameComparator класа имплементира интерфейса
		// Comparable. Ако не го подадем ще получим изключение по време на изпълнение на програмата
		Set<NameComparator> namesComparable = new TreeSet<>();
		
		// Name не имплементира Comparable и използваме ламбда изрази и анонимни класове,
		// за да подадем Comparator на конструктура
		Set<Name> namesLambda = new TreeSet<>((n1, n2)-> {
			int lastCmp = n1.getLastName().compareTo(n2.getLastName());
	        return (lastCmp != 0 ? lastCmp :n1.getFirstName().compareTo(n2.getFirstName()));	
		});
		
        namesLambda.add(new Name("John", "Lennon"));
        namesLambda.add(new Name("Karl", "Marx"));
        namesLambda.add(new Name("Groucho", "Marx"));
        namesLambda.add(new Name("Oscar", "Grouch"));
        
        // Използваме потоци, за да филтрираме колекцията като подаваме ламбда израз
        // на който трябва да отговарят всички филтрирани елементи
        namesLambda.stream().filter(n->n.getLastName().startsWith("L"))
        						        .forEach(System.out::println);
        
        int[] integers = new int[20];
        for(int i = 0; i < 20; i++) {
        	integers[i] = i;
        }
        
        System.out.println(Arrays.toString(integers));
        System.out.println(namesLambda);
        

        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        for(int num : numbers) {
            check(num, (Integer x)->{return x%2==0;});
        }
		
		writeToFile("mpr.txt", "wohoo");
	}
	
    public static void check(int number, Predicate<Integer> p) {
        if (p.test(number)) {
            System.out.println("Even: " + number);
        }
    }
    
    public static void writeToFile(String fileName, String content) {
    	
    	File f = new File(fileName);
    	
    	try (OutputStream out = new FileOutputStream(f)) {
    		out.write(content.getBytes());
    		out.flush();
     	} catch (IOException e) {
     		e.printStackTrace();
     	}
    }
}
