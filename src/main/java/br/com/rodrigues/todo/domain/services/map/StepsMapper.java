package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StepsMapper {

    public Step toEntity(String toDoListId, StepsRequestDTO dto) {
        return new Step(
                dto.description(),
                toDoListId
        );
    }

    public StepsResponseDTO toDto(Step entity) {
        return new StepsResponseDTO(
                entity.getId(),
                entity.getDescription(),
                entity.getIsDone(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getToDoListId()
        );
    }

    public List<Step> toListEntity(String toDoListId, List<StepsRequestDTO> steps) {
        return steps.stream().map(dto -> toEntity(toDoListId, dto)).toList();
    }

    public List<StepsResponseDTO> toListDto(List<Step> steps) {
        return steps.stream().map(this::toDto).toList();
    }

    public Step updateEntity(Step entity, StepsRequestDTO dto) {

        entity.setDescription(dto.description() == null ? entity.getDescription() : dto.description());
        entity.setIsDone(dto.done() == null ? entity.getIsDone() : dto.done());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }

}
