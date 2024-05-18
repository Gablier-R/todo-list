package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.repositories.StepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor

@Component
public class ToDoMapper {

    private final StepRepository stepRepository;

    public ToDoResponseDTO toDto(ToDoList entity) {
        return new ToDoResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getCategory(),
                entity.getPriority(),
                entity.getIsDone(),
                entity.getLimitDate(),
                entity.getIsExpired(),
                stepRepository.countByToDoListId(entity.getId()),
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
                dto.category(),
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
