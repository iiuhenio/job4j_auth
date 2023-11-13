package ru.job4j.persons.domain;

import lombok.*;
import ru.job4j.persons.validation.Operation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private long id;
    @NotBlank(message = "Login must be not empty")
    private String login;

    @NotBlank(message = "Password must be not empty")
    private String password;

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{"
                + "id=" + id
                + ", login='" + login + '\''
                + '}';
    }
}
