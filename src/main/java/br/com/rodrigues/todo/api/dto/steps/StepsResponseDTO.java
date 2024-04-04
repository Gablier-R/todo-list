package br.com.rodrigues.todo.api.dto.steps;

import java.time.LocalDateTime;

public record StepsResponseDTO(
        String id,
        String description,
        Boolean done,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String toDoListId
) {
}
