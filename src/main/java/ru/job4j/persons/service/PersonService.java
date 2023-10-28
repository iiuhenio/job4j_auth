package ru.job4j.persons.service;

import ru.job4j.persons.domain.Person;

import java.util.List;

public interface PersonService {

    Person create(Person person);

    Person getById(Long id);

    List<Person> getAll();

    Person update(Person person);

    void deleteById(Long id);
}