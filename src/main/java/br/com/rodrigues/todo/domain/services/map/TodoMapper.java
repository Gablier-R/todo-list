package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.todo.TodoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.TodoResponseDTO;
import br.com.rodrigues.todo.domain.entities.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor

@Component
public class TodoMapper {

    private final StepsMapper stepsMapper;

    public TodoResponseDTO toDto (Todo entity){
        return new TodoResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getPriorities(),
                entity.getDone(),
                stepsMapper.toListDto(entity.getSteps()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public Todo toEntity (TodoRequestDTO dto){
        return new Todo(
                dto.name(),
                dto.priorities()
        );
    }

    public List<TodoResponseDTO> toList(List<Todo> todoList) {
        return todoList.stream().map(this::toDto).toList();
    }

    public Todo update ()
}
