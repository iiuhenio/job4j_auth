package ru.job4j.persons.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.persons.domain.Person;
import ru.job4j.persons.service.PersonService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;


    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Optional<Person> result = Optional.ofNullable(personService.create(person));
        return new ResponseEntity<>(
                result.orElse(person),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> findById(@PathVariable long id) {
        return ResponseEntity.ok(personService.getById(id));
    }

    @PutMapping("/")
    public boolean update(Person person) {
        boolean updated = false;
        try {
            personService.create(person);
            updated = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updated;
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(long id) {
        boolean deleted = false;
        try {
            personService.deleteById(id);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }
}