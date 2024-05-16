package br.com.rodrigues.todo.api.dto.user;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

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

        @NotBlank(message = "Date cannot be blank")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Past(message = "Date must be in the past")
        Date dateOfBirth,

        @NotNull(message = "A senha não pode ser nula.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,20}$",
                message = "A senha deve ter entre 6 e 20 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.")
        String password
) {
}
