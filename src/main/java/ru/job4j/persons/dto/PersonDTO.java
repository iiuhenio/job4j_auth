package ru.job4j.persons.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PersonDTO {

    @NotNull(message = "Id must be non null")
    private long id;

    @Size(min = 5, max = 15, message
            = "Incorrect password")
    private String password;
}
