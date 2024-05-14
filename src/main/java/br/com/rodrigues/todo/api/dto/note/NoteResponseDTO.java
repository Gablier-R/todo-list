package br.com.rodrigues.todo.api.dto.note;

import java.time.LocalDateTime;

public record NoteResponseDTO(

        String id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String userId
) {
}
