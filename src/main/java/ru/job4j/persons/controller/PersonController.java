package ru.job4j.persons.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.persons.domain.Person;
import ru.job4j.persons.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@RequiredArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder encoder;

    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody Person person) {

        if (person.getLogin() == null || person.getPassword() == null) {
            throw new NullPointerException("Login and password can't be empty");
        }

        if (person.getPassword().length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }

        person.setPassword(encoder.encode(person.getPassword()));

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
        if ((this.personService.getById(id))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
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

    @GetMapping("/example1")
    public ResponseEntity<?> example1() {

        //return ResponseEntity.ok("Example1");
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    @GetMapping("/example2")
    public ResponseEntity<Map<String, String>> example2() {
        return ResponseEntity.of(Optional.of(new HashMap<>() {{
            put("key", "value");
        }}));
    }

    @GetMapping("/example3")
    public ResponseEntity<?> example3() {
        Object body = new HashMap<>() {{
            put("key", "value");
        }};
        var entity = new ResponseEntity(
                body,
                new MultiValueMapAdapter<>(Map.of("Job4jCustomHeader", List.of("job4j"))),
                HttpStatus.OK
        );
        return entity;
    }

    @GetMapping("/example4")
    public ResponseEntity<String> example4() {
        var body = new HashMap<>() {{
            put("key", "value");
        }}.toString();
        var entity = ResponseEntity.status(HttpStatus.CONFLICT)
                .header("Job4jCustomHeader", "job4j")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(body.length())
                .body(body);
        return entity;
    }

    @GetMapping("/example5")
    public ResponseEntity<byte[]> example5() throws IOException {
        var content = Files.readAllBytes(Path.of("./book.pdf"));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(content.length)
                .body(content);
    }

    @GetMapping("/example6")
    public byte[] example6() throws IOException {
        return Files.readAllBytes(Path.of("./pom.xml"));
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}