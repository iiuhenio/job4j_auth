package ru.job4j.persons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.persons.domain.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}