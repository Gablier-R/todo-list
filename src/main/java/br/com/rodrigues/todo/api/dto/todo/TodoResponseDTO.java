package br.com.rodrigues.todo.api.dto.todo;

import br.com.rodrigues.todo.domain.entities.Priorities;

import java.time.LocalDateTime;

public record TodoResponseDTO(
        String id,
        String name,
        Priorities priorities,
        Boolean done,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
