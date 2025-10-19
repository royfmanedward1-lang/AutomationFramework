package com.example;

import com.example.model.Person;
import com.example.service.PersonService;

public class App {
    public static void main(String[] args) {
        // Create instances of Person
        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Bob", 25);

        // Create a PersonService instance
        PersonService personService = new PersonService();

        // Add persons to the service
        personService.addPerson(person1);
        personService.addPerson(person2);

        // Retrieve and display all persons
        System.out.println("List of Persons:");
        for (Person person : personService.getAllPersons()) {
            System.out.println("Name: " + person.getName() + ", Age: " + person.getAge());
        }
    }
}