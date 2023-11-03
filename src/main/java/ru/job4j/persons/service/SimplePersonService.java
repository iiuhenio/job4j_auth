package ru.job4j.persons.service;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.persons.domain.Person;
import ru.job4j.persons.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Collections.emptyList;


@Service
@NoArgsConstructor
@AllArgsConstructor
public class SimplePersonService implements PersonService {

    private PersonRepository personRepository;


    @Override
    public Optional<Person> create(Person person) {
            return Optional.of(personRepository.save(person));
    }

    @Override
    public Optional<Person> getById(Long id) {
        return Optional.ofNullable(personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Boolean update(Person person) {
        boolean updated = false;
        var person1 = personRepository.findById(person.getId());
        if (person1.isPresent()) {
            personRepository.save(person);
            updated = true;
        }
        return updated;
    }

    @Override
    public Boolean deleteById(Long id) {
        boolean deleted = false;
        var person = personRepository.findById(id);
        if (person.isPresent()) {
            personRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public Optional<Person> findByLogin(String login) {
        return personRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByLogin(username)
                .map(person ->  new User(person.getLogin(), person.getPassword(), emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
