package br.com.rodrigues.todo.api.dto.user;

import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record UserResponseDTO(

        String id,
        String name,
        String lastName,
        String email,
        Date dateOfBirth,
        List<ToDoResponseDTO> lists,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
