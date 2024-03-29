package br.com.rodrigues.todo.api.dto.user;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserRequestDTO(

        @NotNull
        @NotBlank
        @Size( min = 3)
        String name,

        @NotNull
        @NotBlank
        @Size( min = 3)
        String lastName,

        @NotNull
        @NotBlank
        @Email
        String email,

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @Past
        LocalDate dateOfBirth
) {
}
