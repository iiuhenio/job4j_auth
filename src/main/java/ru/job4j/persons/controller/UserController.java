package ru.job4j.persons.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.persons.domain.Person;
import ru.job4j.persons.repository.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private PersonRepository users;
    private BCryptPasswordEncoder encoder;


    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        users.save(person);
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return users.findAll();
    }
}