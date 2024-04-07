package br.com.rodrigues.todo.api.dto.steps;

import jakarta.validation.constraints.Size;

public record StepsRequestDTO(

        @Size(min = 3, message = "Minimum size of 3")
        String description,
        Boolean done
) {
}
