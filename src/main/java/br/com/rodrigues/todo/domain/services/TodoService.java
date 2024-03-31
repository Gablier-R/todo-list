package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.repositories.ToDoRepository;
import br.com.rodrigues.todo.domain.services.map.ToDoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class TodoService {

    private final ToDoRepository todoRepository;
    private final ToDoMapper todoMapper;

    public ToDoResponseDTO saveToDo(ToDoRequestDTO dto) {
        var entity = todoMapper.toEntity(dto);

        var response = save(entity);

        return todoMapper.toDto(response);
    }

    public ToDoResponseDTO updateToDo(String id, ToDoRequestDTO dto){
        var entity = findById(id);
        ToDoList toDoListForSave = todoMapper.update(entity, dto);

        updateStatusSteps(toDoListForSave);
        todoRepository.save(toDoListForSave);

        return todoMapper.toDto(toDoListForSave);
    }

    public ToDoResponseDTO detailsToDo(String id) {
        var entity = findById(id);
        return todoMapper.toDto(entity);
    }

    public void updateStatusSteps (ToDoList todoList){

        if (todoList.getIsDone().equals(true)){

            for(Step step: todoList.getSteps()){
                step.setIsDone(true);
            }
            todoRepository.save(todoList);
        }
    }

    public void updateStatusTodoList(ToDoList todoList) {
        boolean allStepsDone = true;

        for (Step step : todoList.getSteps()) {
            if (!step.getIsDone()) {
                allStepsDone = false;
                break;
            }
        }

        if (allStepsDone) {
            todoList.setIsDone(true);
            todoRepository.save(todoList);
        }
    }



    public ToDoList findById(String id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exists"));
    }

    public List<ToDoResponseDTO> findAllToDo(){
        return todoMapper.toList(todoRepository.findAll());
    }

    public void deleteTodoById (String  id){
        todoRepository.deleteById(id);
    }

    public ToDoList save (ToDoList todoList){
        return todoRepository.save(todoList);
    }
}
