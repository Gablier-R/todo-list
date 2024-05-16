package br.com.rodrigues.todo.api.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

        @NotBlank
        @NotNull
        @Size(min = 3, message = "Minimum size of 1")
        String name
) {
}
