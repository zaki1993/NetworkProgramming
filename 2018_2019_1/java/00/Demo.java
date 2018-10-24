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
		/*Cat child = new HouseCat("Gosho");
		Cat hc = new HouseCat("Pesho", child);
		hc.speak();
		hc.getChild().speak();
		hc.jump();
		*/
		//hc.doHouseStuff();
		
		/*Set<Name> names = new TreeSet<>((n1, n2)-> {
			int lastCmp = n1.getLastName().compareTo(n2.getLastName());
	        return (lastCmp != 0 ? lastCmp :n1.getFirstName().compareTo(n2.getFirstName()));	
		});
		
        names.add(new Name("John", "Lennon"));
        names.add(new Name("Karl", "Marx"));
        names.add(new Name("Groucho", "Marx"));
        names.add(new Name("Oscar", "Grouch"));
        
        names.stream().filter(n->n.getLastName().startsWith("L"))
        						  .forEach(System.out::println);
        */
        /*int[] integers = new int[20];
        for(int i = 0; i < 20; i++) {
        	integers[i] = i;
        }*/
        /*
        System.out.println(Arrays.toString(integers));
        System.out.println(names);
        */

        /*List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        for(int num : numbers) {
            check(num, (Integer x)->{return x%2==0;});
        }*/
		
		
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
