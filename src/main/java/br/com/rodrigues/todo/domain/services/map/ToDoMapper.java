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

    public ToDoResponseDTO toDto (ToDoList entity){
        return new ToDoResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getPriorities(),
                entity.getLimitDate(),
                entity.getIsDone(),
                stepsMapper.toListDto(entity.getSteps()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ToDoList toEntity(ToDoRequestDTO dto){
        return new ToDoList(
                dto.name(),
                dto.priorities(),
                dto.limitDate()
        );
    }

    public List<ToDoResponseDTO> toListDto(List<ToDoList> toDoList) {
        return toDoList.stream().map(this::toDto).toList();
    }


    public ToDoList update (ToDoList entity, ToDoRequestDTO dto){

        entity.setName(dto.name() == null ? entity.getName() : dto.name());
        entity.setPriorities(dto.priorities() == null ? entity.getPriorities() : dto.priorities());
        entity.setIsDone(dto.done() == null ? entity.getIsDone() : dto.done());
        entity.setUpdatedAt(LocalDateTime.now());

        return  entity;
    }

}
