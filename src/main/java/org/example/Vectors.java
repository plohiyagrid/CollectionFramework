package org.example;

//dynamics array
//synchronized -> thread safe

import java.util.Arrays;
import java.util.Vector;

public class Vectors {
    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();  //10 by default
        System.out.println(vector.capacity());

        Vector<Integer> vector2 = new Vector<>(11);
        System.out.println(vector2.capacity());

        Vector<Integer> vector3 = new Vector<>(1 , 5); //capacity increment  is 5
        System.out.println(vector3.capacity());
        vector3.add(10);
        vector3.add(12);
        System.out.println(vector3.capacity());

        //Constructor
        Vector<Integer> vectorConst = new Vector<>(Arrays.asList(1,3,45,2)); //collection



    }
}
