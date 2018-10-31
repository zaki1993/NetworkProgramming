package com.fmi.mpr.e_01.p_02;

public class AutoboxingExample {
	
	public static void main(String[] args) {
		
/*		int x = new Integer(5); // autounboxing
		
		Integer y = 5; // autoboxing 
		
		Integer.parseInt("123");
		Integer.valueOf("123");
		
		if (x == 5) {
			System.out.println("x="+x);
		}
		if (y == 5) {
			System.out.println("y=" + y);
		}
		if (y == x) {
			System.out.println("y=x");
		}*/
		
		
		Integer xx = -129;
		Integer yy = -129;
		if (yy.equals(xx)) {
			System.out.println("equals");
		}
		
		if (yy == xx) {
			System.out.println("xx=yy");
		}
		
		/*Box b = new Box();
		b.test(1);
		
		b.test(new Integer(123));*/
		
		
		//Integer wrapperX;
		
		
		StringBuilder tst = new StringBuilder("123");
		doSomething(tst);
		System.out.println(tst);
		
	}
	
	public static void doSomething(StringBuilder s) {
		s.append("test");
	}
}
