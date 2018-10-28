package com.fmi.mpr.e_00;

public class NameComparable implements Comparable<NameComparable> {
    
	private String firstName;
    private String lastName;

    NameComparable(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public int compareTo(NameComparable o) {
        int lastCmp = lastName.compareTo(o.lastName);
        return (lastCmp != 0 ? lastCmp :firstName.compareTo(o.firstName));
    }

    public String toString() {
        return firstName + " " + lastName;
    }
}
