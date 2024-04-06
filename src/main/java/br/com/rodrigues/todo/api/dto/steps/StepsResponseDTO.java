package br.com.rodrigues.todo.api.dto.steps;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public record StepsResponseDTO(
        String id,
        String description,
        Boolean done,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        @JsonIgnore
        String toDoListId
) {
}
