package ru.job4j.persons.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.persons.domain.Person;
import ru.job4j.persons.repository.PersonRepository;

import java.util.List;


@Service
public class SimplePersonService implements PersonService {

    private PersonRepository personRepository;

    public SimplePersonService() {
    }

    @Override
    public Person create(Person person) {
        return personRepository.save(person);

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
    public Person update(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }
}