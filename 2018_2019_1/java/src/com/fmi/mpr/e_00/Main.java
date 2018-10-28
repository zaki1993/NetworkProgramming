package com.fmi.mpr.e_00;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
    	
    	
    	
        Set<Name> names = new TreeSet<>((n1, n2) -> {
            int lastCmp = n1.getLastName().compareTo(n2.getLastName());
            return (lastCmp != 0 ? lastCmp :n1.getFirstName().compareTo(n2.getFirstName()));
        });

        names.add(new Name("John", "Lennon"));
        names.add(new Name("Karl", "Marx"));
        names.add(new Name("Groucho", "Marx"));
        names.add(new Name("Oscar", "Grouch"));

        System.out.println(names);

        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        for(int num : numbers) {
            check(num, (Integer x)->{return x%2==0;});
        }

        Person[] people = {
                    new Person("John", 1000),
                    new Person("Karl", 2000),
                    new Person("Petar", 1000),
                    new Person("Georgi", 2000),
                    new Person("Test", 1000),
                    new Person("Ivan", 2000)
                };
        /*for (Person p : people) {
            p.start();
        }*/

        writeToFile("test 1 2 3 ", "mpr.txt");

        System.out.println(readFile("mpr.txt"));

        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        System.out.println("X is " + x);
    }

    public static void check(int number, Predicate<Integer> p) {
        if (p.test(number)) {
            System.out.println("Even: " + number);
        }
    }

    public static String readFile(String fileName) {

        File file = new File(fileName);
        byte[] buffer = new byte[1024];
        StringBuilder content = new StringBuilder();
        try (InputStream in = new FileInputStream(file)) {
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer, 0, 1024)) > 0) {
                content.append(new String(buffer, 0, bytesRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void writeToFile(String content, String fileName) {

        File file = new File(fileName);

        try (OutputStream out = new FileOutputStream(file)) {
            out.write(content.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
