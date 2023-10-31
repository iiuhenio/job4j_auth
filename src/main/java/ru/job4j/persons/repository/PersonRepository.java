package ru.job4j.persons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.persons.domain.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByLogin(String login);
}