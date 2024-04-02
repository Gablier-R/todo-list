package br.com.rodrigues.todo.api.dto.todo;

import br.com.rodrigues.todo.domain.entities.Priorities;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ToDoRequestDTO(

        @Size(min = 3, message = "Minimum size of 3")
        String name,
        Priorities priorities,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Future(message = "Date must be in the future")
        LocalDate limitDate,
        Boolean done
) {
}
