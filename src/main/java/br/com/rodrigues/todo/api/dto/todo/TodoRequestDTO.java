package br.com.rodrigues.todo.api.dto.todo;

import br.com.rodrigues.todo.domain.entities.Priorities;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TodoRequestDTO(

        @NotNull
        @NotBlank
        @Size( min = 3)
        String name,
        Priorities priorities
) {
}
