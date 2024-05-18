package br.com.rodrigues.todo.api.dto.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NoteRequestDTO(

        @NotBlank
        @NotNull
        @Size(min = 1, message = "Minimum size of 1")
        String content,

        String category
) {
}
