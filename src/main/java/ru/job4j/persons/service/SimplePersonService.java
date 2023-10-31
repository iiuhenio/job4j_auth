package ru.job4j.persons.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.persons.domain.Person;
import ru.job4j.persons.repository.PersonRepository;

import java.util.List;
import java.util.Optional;


@Service
public class SimplePersonService implements PersonService {

    private PersonRepository personRepository;

    public SimplePersonService() {
    }

    @Override
    public Optional<Person> create(Person person) {
            return Optional.of(personRepository.save(person));
    }

    @Override
    public Person getById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Boolean update(Person person) {
        personRepository.save(person);
        return personRepository.findById(person.getId()).isPresent();
    }

    @Override
    public Boolean deleteById(Long id) {
            boolean deleted = false;
            try {
                personRepository.deleteById(id);
                deleted = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return deleted;
        }
    }
