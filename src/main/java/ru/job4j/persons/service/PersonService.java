package ru.job4j.persons.service;

import ru.job4j.persons.domain.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface PersonService extends UserDetailsService {

    Optional<Person> create(Person person);

    Optional<Person> getById(Long id);

    List<Person> getAll();

    Boolean update(Person person);

    Boolean deleteById(Long id);

    Optional<Person> findByLogin(String login);
}