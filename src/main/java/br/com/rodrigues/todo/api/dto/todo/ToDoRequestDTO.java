package br.com.rodrigues.todo.api.dto.todo;

import br.com.rodrigues.todo.domain.entities.Priorities;
import jakarta.validation.constraints.Size;

public record ToDoRequestDTO(

        @Size( min = 3)
        String name,
        Priorities priorities,
        Boolean done
) {
}
