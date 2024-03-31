package br.com.rodrigues.todo.api.dto.todo;

import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.entities.Priorities;

import java.time.LocalDateTime;
import java.util.List;

public record ToDoResponseDTO(
        String id,
        String name,
        Priorities priorities,
        Boolean done,
        List<StepsResponseDTO> steps,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
