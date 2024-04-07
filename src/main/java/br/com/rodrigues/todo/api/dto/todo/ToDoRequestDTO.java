package br.com.rodrigues.todo.api.dto.todo;

import br.com.rodrigues.todo.domain.entities.Priority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ToDoRequestDTO(

        @Size(min = 3, message = "Minimum size of 3")
        String name,
        Priority priority,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Future(message = "Date must be in the future")
        LocalDate limitDate,
        Boolean done
) {
}
