//Problem Statement

/*
public class Person {
  private String id;
  private String name;
  private List<Address> addresses;

  // getters and setters
}


public class Address {
  private String name;
  private String city;
  private String zipCode;
  // getters and setters
}



/*List of Persons is given write a program for the following requirement to output the array of person names
 *
 *  Input -> List of persons, Output -> Array of Person names
 *
 *  1. Remove all duplicate Persons objects
 *  2. Sort Persons by their name in descending order
 *  3. Extract only 3 Persons whose address in city 'New York' or Person name contains 'John'
 *  4. Get Person name in UPPER CASE
 *
 *  Free to add all necessary code changes to fulfil the above requirement.
 *
 */



package org.example;

import java.util.*;

public class InterviewProblem {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("1", "John Smith", List.of(new Address("Home", "New York", "10001"))),
                new Person("2", "Alice Johnson", List.of(new Address("Office", "New York", "10002"))),
                new Person("3", "Bob Brown", List.of(new Address("Home", "Los Angeles", "90001"))),
                new Person("4", "John Doe", List.of(new Address("Home", "Chicago", "60601"))),
                new Person("1", "John Smith", List.of(new Address("Home", "New York", "10001"))), // duplicate
                new Person("5", "Mike Tyson", List.of(new Address("Home", "New York", "10001")))
        );


        String[] result = persons.stream()
                .distinct()
                .sorted(Comparator.comparing(Person::getName).reversed())
                .filter(p ->
                        p.getAddresses().stream().anyMatch(a -> a.getCity().equalsIgnoreCase("new york")) ||
                                p.getName().toLowerCase().contains("john")
                )
                .limit(3)
                .map(p -> p.getName().toUpperCase())
                .toList().toArray(String[]::new);

        System.out.println("Filtered Names:");
        Arrays.stream(result).forEach(System.out::println);
    }
}

class Person {
    private final String id;
    private final String name;
    private final List<Address> addresses;

    public Person(String id, String name, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.addresses = addresses;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Address> getAddresses() { return addresses; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(id, person.id) &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

class Address {
    private final String name;
    private final String city;
    private final String zipCode;

    public Address(String name, String city, String zipCode) {
        this.name = name;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getName() { return name; }
    public String getCity() { return city; }
    public String getZipCode() { return zipCode; }
}
