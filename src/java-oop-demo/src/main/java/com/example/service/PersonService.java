package com.example.service;

import com.example.model.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    private List<Person> personList;

    public PersonService() {
        this.personList = new ArrayList<>();
    }

    public void addPerson(Person person) {
        personList.add(person);
    }

    public List<Person> getAllPersons() {
        return personList;
    }
}