package org.example;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        Stream<Integer> generateStream1 = Stream.generate(() -> 1);
        List<Integer> generateStream = Stream.iterate(1 , x->x+1).limit(10).toList();
        generateStream.forEach(x -> System.out.print( + x + ", "));
        System.out.print("\n");

        //Intermediate Operations
        List<String>list = Arrays.asList("Akshit" , "Ram" , "Shyam" , "Ghanshyam");
        long count = list.stream().filter(x->x.startsWith("A")) //no filtering is done
                .count(); //need terminal operation to use filtering
        System.out.println("No. of names start with 'a' = " + count);

        Stream<String> stringStream = list.stream().map(String::toUpperCase);

        //Sorting
        Stream<String> sortedAscStream = list.stream().sorted();
        Stream<String> sortedDecStream = list.stream().sorted((a,b) -> a.length() - b.length());

        //Distinct
        Stream<String> DistictStream = list.stream().sorted().distinct();

        //limit
        Stream<String> DistictLimitedStream = list.stream().sorted().distinct().limit(3);

        //skip -> starting x elements are skipped
        Stream<String> SkipStream = list.stream().sorted().distinct().skip(3);


        //Terminal Operations
        List<Integer>IntegerList = Arrays.asList(1,2,3,4,5,6);
        Stream<Integer>IntegerStream = IntegerList.stream();

        //collect
        List<Integer>accumulatedList = IntegerList.stream().skip(1).collect(Collectors.toList());

        //forEach
        IntegerList.stream().forEach(x-> System.out.print(x + ","));
        System.out.print("\n");
        // reduce : combines elements to produce single result
        Optional<Integer> reducedSum = IntegerList.stream().reduce((x, y)-> x+y); //accumulator
        Optional<Integer> reducedSumation = IntegerList.stream().reduce(Integer::sum);  //both are same
        System.out.println("Reduced Sum : " + reducedSumation.get());

        //count -> used above


        //Short Circuit operations -> runs till the condition is not satisfied
        // anyMatch , allMatch , noneMatch
        System.out.println("anyMatch : " + IntegerList.stream().anyMatch(x-> x%2 == 0));
        System.out.println("allMatch : " + IntegerList.stream().allMatch(x-> x >= 0));
        System.out.println("noneMatch : " + IntegerList.stream().noneMatch(x-> x < 0));

        //findFirst , findAny
        System.out.println("findFirst : " + IntegerList.stream().findFirst().get());
        System.out.println("findAny : " + IntegerList.stream().findAny()); //return ans in optional wrapper

        //List names of people whose length is grater then 3
        List<String>People = list.stream().filter(x -> x.length()>3).toList();
        People.forEach( x -> System.out.print(x + ", "));
        System.out.println();

        //Squaring and Soring no,
        List<Integer>num = Arrays.asList(1,4,63,8,2);
        num = num.stream().map(x -> x*x).sorted().toList();
        num.forEach( x -> System.out.print(x + ", "));
        System.out.println();

        //Counting occurrences
        String s = "My name is Priyansh Lohiya ,I am a Junior java developer";

        IntStream streamUsingChars = s.chars(); //IntStream -> as each char has corresponding integer value

        System.out.println("No. of occurrences of a : " + s.chars().filter(x->x=='a').count());

        //Stateful Operations -> sorting as need knowledge of other elements present;
        //Stateless Operations -> squaring , filtering as it doesn't need knowledge of other element

        //lazy operation or lazy evaluations
        //--> before terminal operations no intermediate operations will be executed.

        //-------------------------------------------------------------------------------------------------

        //Parallel Streams -->
        //For parallel Processing
        //Allowing multiple threads to process parts of stream simultaneously
        //to improve performance

        //Factorial
        long startTime = System.currentTimeMillis();
        List<Integer> intStream = Stream.iterate(1 , x -> x+1).limit(20000).toList();
        List<Long> Factoriallist1 = intStream.stream().map(StreamDemo::factorial).toList();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for list1 : " + (long)(endTime - startTime));

        long startTime2 = System.currentTimeMillis();
        List<Integer> intStream2 = Stream.iterate(1 , x -> x+1).limit(20000).toList();
        List<Long> Factoriallist2 = intStream.parallelStream().map(StreamDemo::factorial).toList();
        long endTime2 = System.currentTimeMillis();
        System.out.println("Time taken for list2 parallel comp. : " + (long)(endTime2 - startTime2)); //takes significantly less time for large computations


        //Problem with paralle streams

        //sum will be correct
        List<Integer> numb = Arrays.asList(1,2,3,4,5);
        System.out.println("Sum of 1,2,3,4,5 = 15 ");
        System.out.println("Stream : " + numb.stream().reduce(Integer::sum).get());
        System.out.println("ParallelStream : " + numb.parallelStream().reduce(Integer::sum).get());

        System.out.println("Cumulative of 1,2,3,4,5 ");
        AtomicInteger sum = new AtomicInteger(0); //to make it thread safe
        System.out.println("Stream : " + numb.stream().map(sum::getAndAdd).toList());
        sum.set(0); //to make it thread safe
        System.out.println("ParallelStream : " + numb.parallelStream().map(sum::getAndAdd).toList()); //provides wrong ans
        //sequential can be usd to make parallel stream sequential

        //peek --> intermediate operation to perform action
        Object[] arr = Stream.iterate(1, x->x+1).limit(10).peek(System.out::print).toArray();
        //toArray
        System.out.println();
        //max/min
        System.out.println("Max: = " + Stream.of(1,3,4,4,32,1).max((o1, o2) -> o1 - o2).get());
        System.out.println("Min: = " + Stream.of(1,3,4,4,32,1).min(Comparator.naturalOrder()).get());

        //flatmap -> transform and flatten
        List<List<String>> fruits = Arrays.asList(
                Arrays.asList("Kivi" , "Orange" , "Banana"),
                Arrays.asList("Apple" , "Guava" , "Grapes")
        );
        System.out.println(fruits);

        System.out.printf(String.valueOf(fruits.stream().flatMap(Collection::stream).map(String::toUpperCase).toList()));

        //forEachOrdered
        List<Integer> nums = Arrays.asList(1,2,4,3,5,8);
        System.out.println("For each ordered in ParallelStream : ") ;
        nums.parallelStream().forEachOrdered(System.out::println);

        //Collectors --> Utility class

        // 1. Collecting to a List
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> res = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println(res);

        // 2. Collecting to a Set
        List<Integer> number = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        Set<Integer> set = number.stream().collect(Collectors.toSet());
        System.out.println(set);

        // 3. Collecting to a Specific Collection
        ArrayDeque<String> collect = names.stream().collect(Collectors.toCollection(() -> new ArrayDeque<>()));

        // 4. Joining Strings
        // Concatenates stream elements into a single String
        String concatenatedNames = names.stream().collect(Collectors.joining(""));
        System.out.println(concatenatedNames);

        // 5. Summarizing Data
        // Generates statistical summary (count, sum, min, average, max)

        IntSummaryStatistics stats = number.stream().collect(Collectors.summarizingInt(x -> x));
        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Average: " + stats.getAverage());
        System.out.println("Max: " + stats.getMax());

        //calculating Averages
        Double avg =  numbers.stream().collect(Collectors.averagingInt(x->x));
        System.out.println("Avg:" + avg);

        //Counting Elements
        System.out.println("Count: " + numbers.stream().collect(Collectors.counting()));

        //Grouping Elements
        List<String>words = Arrays.asList("Hello" , "World" , "Java" , "Streams");
        System.out.println(words.stream().collect(Collectors.groupingBy(String::length)));
        System.out.println(words.stream().collect(Collectors.groupingBy(String::length , Collectors.joining(", "))));
        System.out.println(words.stream().collect(Collectors.groupingBy(String::length , Collectors.counting())));
        TreeMap<Integer , Long>treemap =  words.stream().collect(Collectors.groupingBy(String::length , TreeMap::new , Collectors.counting()));
        //Portioning Elements
        System.out.println(treemap);
        System.out.println(words.stream().collect(Collectors.partitioningBy(x -> x.length() < 5)));

        //Mapping and collecting -> apply mapping before collection
        System.out.println(words.stream().collect(Collectors.mapping(x -> x.toUpperCase() ,Collectors.toList())));
        System.out.println(words.stream().map(String::toUpperCase).collect(Collectors.toList()));

        //Primitive Stream

        int []n = {1,3,55,3,2};
        IntStream integerStream = Arrays.stream(n);

        System.out.println(IntStream.range(1,10).boxed().collect(Collectors.toList()));
        //boxed is to convert it to wrapper class

        System.out.println(new Random().doubles(5).boxed().toList());
        System.out.println(new Random().ints(5).boxed().toList());
    }
    private static long factorial(long n){
        long res = 1;
        for(int i = 2 ; i <= n ; i++){
            res += i;
        }
        return res;
    }
}
