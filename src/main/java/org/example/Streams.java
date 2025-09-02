package org.example;

import javax.management.ObjectName;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class Streams {

    public static void main(String[] args){

        // functional Programming -> lambda expressions
        Thread t1 = new Thread(() -> {
            System.out.println("Hello World");
        });

        MathOperation sum = (a,b)->a+b;
        MathOperation sub = (a,b)->a-b;

        int resAdd = sum.operate(2,3);
        int resSub = sub.operate(3,2);

        System.out.println("add = " + resAdd + "\n" + "sub = " + resSub);


        //predicate -- functional interface (boolean value function)
        //used to check a condition i.e test method
        //predicate holds a condition
        Predicate <Integer> isEven = x->x%2 == 0;
        System.out.println("4 is a even no. = " + isEven.test(4));

        Predicate <String> startWithA = x->x.toLowerCase().startsWith("a");
        Predicate <String> EndsWithA = x->x.toLowerCase().endsWith("a");
        Predicate <String> BothEndsAreA = startWithA.and(EndsWithA); //Combining Predicate methods
        System.out.println("Aman start with 'a' = " + startWithA.test("Aman"));
        System.out.println("Raman start with 'a' = " + startWithA.test("Raman"));
        System.out.println("Anna has 'a' on both ends = " + BothEndsAreA.test("Anna"));

        // function
        // only one abstract methods is present i.e. apply
        // take a input and return a output --> work for you
        Function<Integer , Integer> doubleIt = x -> x*2;
        Function<Integer , Integer> tripleIt = x -> x*3;

        System.out.println("double of no. = " + doubleIt.apply(3));
        System.out.println("double it then triple it = " + doubleIt.andThen(tripleIt).apply(3)); //combines two functions
        System.out.println("triple it then double it = " + doubleIt.compose(tripleIt).apply(3));

        // Identity function - returns whatever we feed them
        Function<Object, Object> identity = Function.identity();
        System.out.println("Identity : " + identity.apply(8));


        //Consumer - consumes given variable returns nothing
        Consumer<Integer> print = x-> System.out.println("Print using consumer : " + x);
        print.accept(2);

        List<Integer> list = Arrays.asList(1, 2, 3);
        Consumer<List<Integer>> printList = x -> {
            System.out.print("Printing List : ");
            for(int i : x) System.out.print(i + " ");
            System.out.print("\n");
        };
        printList.accept(list);

        // Supplier -> takes nothing but provides/returns output
        // Used to get database connections or something similar to that
        Supplier <String>getHelloWorld = ()->"Hello World";
        System.out.println("Supplier : " + getHelloWorld.get());

        //Bi-Predicate , Bi-Consumer , Bi-Function
        BiPredicate<Integer , Integer> isSumEven = (x,y)->(x + y)%2 == 0;
        System.out.println("Check Sum is Even '1' & '3' using Bi-Function: " + isSumEven.test(1 , 3));

        BiConsumer<Integer , String> biConsumer = (x,y) -> {
            System.out.println("Bi-Consumer o/p 1st : " + x);
            System.out.println("Bi-Consumer o/p 2nd : " + y);
        };
        biConsumer.accept(1,"Consume");

        BiFunction<String , String , Integer> lengthOfStringsCombined = (x,y) -> (x+y).length() ;
        System.out.println("Length of combined string 'a' and 'bcd' using bi_function : " + lengthOfStringsCombined.apply("a" , "bcd"));

        // UnaryOperator<Integer> --> removes hassle of Function<Integer,Integer>
        // BinaryOperator<Integer> --> removes hassle of Function<Integer,Integer,Integer>

        //Method Reference ---> Use method without invoking & in place of lambda Expressions
        List<String> students = Arrays.asList("Ram" , "Shyam" , "Gansham");
        System.out.print("Using lambda expression : ");
        students.forEach(x -> System.out.print(x + ", "));
        System.out.print("\nUsing method reference : ");
        students.forEach(System.out::print); //use method directly as reference
        System.out.print("\n");

        //Constructor reference
        List<String> names = Arrays.asList("A" , "B" , "C");
        List<MobilPhone> mobilePhonesUsingLambdaExpression = names.stream().map(x -> new MobilPhone(x)).collect(Collectors.toList());
        List<MobilPhone> mobilePhonesUsingConstructorReference = names.stream().map(MobilPhone::new).toList();


    }
}

class MobilPhone{
    String name;

    MobilPhone(String name){
        this.name = name;
    }
}

interface MathOperation{
    int operate(int a , int b);
}
