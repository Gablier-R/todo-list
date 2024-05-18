package br.com.rodrigues.todo.api.dto.todo;

import br.com.rodrigues.todo.domain.entities.Priority;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ToDoResponseDTO(
        String id,
        String name,
        String category,
        Priority priority,
        Boolean done,
        LocalDate limitDate,
        Boolean isExpired,
        Integer steps,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String userId
) {
}
