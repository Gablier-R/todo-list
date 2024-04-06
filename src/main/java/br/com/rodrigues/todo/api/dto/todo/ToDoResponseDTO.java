package br.com.rodrigues.todo.api.dto.todo;

import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.entities.Priority;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ToDoResponseDTO(
        String id,
        String name,
        Priority priority,
        Boolean done,
        LocalDate limitDate,
        Boolean isExpired,
        List<StepsResponseDTO> steps,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        @JsonIgnore
        String userId
) {
}
