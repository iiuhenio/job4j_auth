package ru.job4j.persons.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.persons.domain.Person;
import ru.job4j.persons.service.PersonService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;


    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody Person person) {

        person.setPassword(BCrypt.hashpw(person.getPassword(), BCrypt.gensalt()));

        Optional<Person> result = personService.create(person);
        return new ResponseEntity<>(
                result.orElse(person),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> findById(@PathVariable long id) {
        return ResponseEntity.ok(personService.getById(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody Person person) {
        if ((this.personService.update(person))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        boolean deleted = personService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person is not deleted");
    }
}