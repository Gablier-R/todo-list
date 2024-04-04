package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.repositories.StepRepository;
import br.com.rodrigues.todo.domain.repositories.ToDoListRepository;
import br.com.rodrigues.todo.domain.services.map.MapPage;
import br.com.rodrigues.todo.domain.services.map.ToDoMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class ToDoListService {

    private final ToDoListRepository toDoListRepository;
    private final StepRepository stepRepository;

    private final ToDoMapper todoMapper;
    private final UserService userService;
    private final MapPage mapPage;


    public ToDoResponseDTO saveToDo(String userId, ToDoRequestDTO dto) {

        userService.validateUser(userId);
        var toDoList = todoMapper.toEntity(userId, dto);

        var response = toDoListRepository.save(toDoList);

        return todoMapper.toDto(response);
    }

    public ToDoResponseDTO updateToDo(String userId, String todoId, ToDoRequestDTO dto) {

        userService.validateUser(userId);

        ToDoList entity = validateToDoList(todoId);

        ToDoList toDoListForSave = todoMapper.update(entity, dto);

        var response = updateStatusToDoListIsDone(toDoListForSave.getSteps(), toDoListForSave);

        toDoListRepository.save(response);

        return todoMapper.toDto(response);
    }

    public ToDoResponseDTO detailsToDo(String userId, String todoId) {
        userService.validateUser(userId);

        return todoMapper.toDto(validateToDoList(todoId));
    }

    public PageableDTO findAllToDo(String userId, Pageable pageable) {
        Page<ToDoList> page = toDoListRepository.findAllToDoListByUserId(userId, pageable);

        var response = todoMapper.toListDto(page.getContent());

        return mapPage.mapToResponseAll(response, page);
    }

    public PageableDTO findAllToDoFilterCustom(String userId, String priority, Pageable pageable) {
        Page<ToDoList> page = toDoListRepository.findAllByUserIdAndPriority(userId, priority, pageable);
        List<ToDoList> filtered = page.getContent();
        var response = todoMapper.toListDto(filtered);
        return mapPage.mapToResponseAll(response, page);
    }

    public void deleteTodoByUserIdAndTodoId(String userId, String todoId) {
        userService.validateUser(userId);

        var todo = validateToDoList(todoId);

        List<Step> stepsForDelete = todo.getSteps();
        stepRepository.deleteAll(stepsForDelete);

        toDoListRepository.deleteById(todoId);
    }


    //Auxiliary methods
    public ToDoList validateToDoList(String todoId) {
        return toDoListRepository.findById(todoId).orElseThrow(() -> new NotFoundException("List does not exists in user"));
    }

    private ToDoList updateStatusToDoListIsDone(List<Step> step, ToDoList todo) {

        boolean verify = true;

        for (Step steps : step) {
            if (!steps.getIsDone()) {
                verify = false;
                break;
            }
        }

        if (verify) {
            todo.setIsDone(true);
        }

        return todo;
    }


}
