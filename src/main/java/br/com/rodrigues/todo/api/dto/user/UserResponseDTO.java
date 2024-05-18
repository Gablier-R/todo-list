package br.com.rodrigues.todo.api.dto.user;

import java.time.LocalDateTime;
import java.util.Date;

public record UserResponseDTO(

        String id,
        String name,
        String lastName,
        String email,
        Date dateOfBirth,
        Integer lists,
        Integer notes,
        Integer categories,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
