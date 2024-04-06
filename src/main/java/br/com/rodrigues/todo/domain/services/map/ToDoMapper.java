package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor

@Component
public class ToDoMapper {

    private final StepsMapper stepsMapper;

    public ToDoResponseDTO toDto(ToDoList entity) {
        return new ToDoResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getPriority(),
                entity.getIsDone(),
                entity.getLimitDate(),
                entity.getIsExpired(),
                stepsMapper.toListDto(entity.getSteps()),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getUserId()
        );
    }

    public ToDoList toEntity(String userId, ToDoRequestDTO dto) {
        return new ToDoList(
                dto.name(),
                dto.priority(),
                dto.limitDate(),
                userId

        );
    }

    public List<ToDoResponseDTO> toListDto(List<ToDoList> toDoList) {
        return toDoList.stream().map(this::toDto).toList();
    }

    public ToDoList update(ToDoList entity, ToDoRequestDTO dto) {

        entity.setName(dto.name() == null ? entity.getName() : dto.name());
        entity.setPriority(dto.priority() == null ? entity.getPriority() : dto.priority());
        entity.setLimitDate(dto.limitDate() == null ? entity.getLimitDate() : dto.limitDate());
        entity.setIsDone(dto.done() == null ? entity.getIsDone() : dto.done());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }

}
