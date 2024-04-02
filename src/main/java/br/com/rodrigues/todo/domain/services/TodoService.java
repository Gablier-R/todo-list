package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.entities.User;
import br.com.rodrigues.todo.domain.repositories.ToDoRepository;
import br.com.rodrigues.todo.domain.services.map.ToDoMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class TodoService {

    private final ToDoRepository todoRepository;
    private final ToDoMapper todoMapper;
    private final UserService userService;

    public ToDoResponseDTO saveToDo(String userId, ToDoRequestDTO dto) {

        var user = userService.validateUser(userId);
        var toDoList = todoMapper.toEntity(dto);

        user.getList().add(toDoList);

        userService.save(user);

        return todoMapper.toDto(toDoList);
    }

    public ToDoResponseDTO updateToDo(String userId, String todoId, ToDoRequestDTO dto) {
        User userValidated = userService.validateUser(userId);
        ToDoList entity = validateListInUser(userValidated, todoId);

        ToDoList toDoListForSave = todoMapper.update(entity, dto);

        // Verifica se a ToDoList está marcada como concluída e atualiza os passos
        if (toDoListForSave.getIsDone()) {
            for (Step step : toDoListForSave.getSteps()) {
                step.setIsDone(true);
            }
        }

        // Atualiza a lista de tarefas do usuário com a ToDoList atualizada
        userValidated.getList().stream()
                .filter(list -> list.getId().equals(todoId))
                .findFirst()
                .ifPresent(list -> list.setSteps(toDoListForSave.getSteps()));

        userService.save(userValidated); // Salva o usuário atualizado

        return todoMapper.toDto(toDoListForSave);
    }


    public ToDoResponseDTO detailsToDo(String userId, String todoId) {

        var user = userService.validateUser(userId);

        return todoMapper.toDto(validateListInUser(user, todoId));
    }

    public List<ToDoResponseDTO> findAllToDo(String userId) {

        return todoMapper.toListDto(findAllListByUserId(userId));
    }

    public void deleteTodoByUserIdAndTodoId(String userId, String todoId) {

        var user = userService.validateUser(userId);
        var todoForDelete = validateListInUser(user, todoId);

        user.getList().remove(todoForDelete);

        userService.save(user);
    }


    //Auxiliary methods
    public ToDoList validateListInUser(User user, String todoId) {

        for (ToDoList list : user.getList()) {
            if (list.getId().equals(todoId)) {
                return list;
            }
        }
        throw new NotFoundException("Todo list not found for user with id: " + user.getId());
    }

    public ToDoList validateToDo(String id) {
        return todoRepository.findById(id).orElseThrow(() -> new NotFoundException("ToDo does not exists"));
    }

    private List<ToDoList> findAllListByUserId(String userId) {

        var user = userService.validateUser(userId);

        return new ArrayList<>(user.getList());
    }

}
