package br.com.rodrigues.todo.api.dto.utils;

import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GeneralResponseDTO(
        String id,
        String name,
        String lastName,
        String email,
        LocalDate dateOfBirth,
        List<ToDoResponseDTO> lists,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
