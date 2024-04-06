package br.com.rodrigues.todo.api.dto.user;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserRequestDTO(

        @NotBlank
        @NotNull
        @Size(min = 3, message = "Minimum size of 3")
        String name,

        @NotBlank
        @NotNull
        @Size(min = 3, message = "Minimum size of 3")
        String lastName,

        @NotBlank
        @NotNull
        @Email
        String email,

        //@NotBlank(message = "Date cannot be blank")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Past(message = "Date must be in the past")
        LocalDate dateOfBirth
) {
}
