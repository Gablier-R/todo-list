package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.todo.TodoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.TodoResponseDTO;
import br.com.rodrigues.todo.domain.entities.Todo;
import br.com.rodrigues.todo.domain.repositories.TodoRepository;
import br.com.rodrigues.todo.domain.services.map.TodoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper mapper;

    public TodoResponseDTO saveTodo(TodoRequestDTO dto) {
        var entity = mapper.toEntity(dto);

        var response = todoRepository.save(entity);

        return mapper.toDto(response);
    }

    public TodoResponseDTO detailsTodo(String id) {
        var entity = findById(id);
        return mapper.toDto(entity);
    }

    public Todo findById(String id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exists"));
    }

    public Todo updateTodoEntity () {

    }
}
