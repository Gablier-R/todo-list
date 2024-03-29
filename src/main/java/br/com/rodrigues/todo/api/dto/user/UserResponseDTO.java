package br.com.rodrigues.todo.api.dto.user;

import br.com.rodrigues.todo.api.dto.todo.TodoResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDTO(
        String id,
        String name,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        List<TodoResponseDTO> todo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
