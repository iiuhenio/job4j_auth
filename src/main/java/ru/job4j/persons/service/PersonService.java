package ru.job4j.persons.service;

import ru.job4j.persons.domain.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<Person> create(Person person);

    Person getById(Long id);

    List<Person> getAll();

    void update(Person person);

    void deleteById(Long id);
}