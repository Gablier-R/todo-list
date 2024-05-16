package br.com.rodrigues.todo.api.dto.category;

import java.time.LocalDateTime;

public record CategoryResponseDTO(

            String id,
            String name,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String userId
) {
}
