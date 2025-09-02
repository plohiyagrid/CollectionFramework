package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {
        //process collections of data in a functional and declarative way
        //simplify data processing
        //Easier to read and maintain
        //use Functional programming
        //Easier to achieve parallelism

        //Source --> Collection ,file anything from where we are getting data
        //Intermediate Operations --> filter(),
        //Terminal Operations --> count(),

        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        System.out.println("Count total even numbers in 1,2,3,4,5 = " + numbers.stream().filter(x->x%2==0).count());

        // Creating Streams  -->

        // 1. Collection as Source
        Stream<Integer> numberStream = numbers.stream();

        // 2. From Arrays
        String[] stringArray = {"a" , "b" , "c"};
        Stream<String> arrayStream1 = Arrays.stream(stringArray);

        // 3. Using Stream.of()
        Stream<String> StingStream = Stream.of("a" , "b" , "c");

        // 4. Infinite Streams
        Stream<Integer> generate = Stream.generate(() -> 1);
        List<Integer> generateStream = Stream.iterate(1 , x->x+1).limit(10).toList();
        generateStream.forEach(x -> System.out.print( + x + ", "));


        //Intermediate Operations

    }
}
