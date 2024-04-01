package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.services.map.StepsMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor

@Service
public class StepsService {

    private final TodoService todoService;
    private final StepsMapper stepsMapper;


    public List<StepsResponseDTO> saveStep (String id, List<StepsRequestDTO>  dto){

        var todo = todoService.validateToDo(id);

        var entity = stepsMapper.toListEntity(dto);

        List<Step> stepList = new ArrayList<>(entity);

        todo.getSteps().addAll(stepList);

        todoService.save(todo);

        return stepsMapper.toListDto(todo.getSteps());
    }

    public List<StepsResponseDTO> listStepsByTodo (String todoId) {
        var todo = todoService.validateToDo(todoId);

        var steps = todo.getSteps();

        return stepsMapper.toListDto(steps);
    }

    public StepsResponseDTO findStepByTodoIdAndStepId(String todoId, String stepId){
        var todo = todoService.validateToDo(todoId);

        var steps = todo.getSteps();

        boolean stepFound = false;
        for (Step step: steps){
            if (step.getId().equals(stepId)){
                stepFound = true;
                return stepsMapper.toDto(step);
            }
        }

        throw new NotFoundException("Step not found in the specified ToDo");
    }

    public StepsResponseDTO updateStep (String todoId, String stepId, StepsRequestDTO stepsResponseDTO){
        var todo = todoService.validateToDo(todoId);

        Step response = null;

        boolean stepFound = false;
        for (Step step: todo.getSteps()){
            if (step.getId().equals(stepId)){
                stepFound = true;
                stepsMapper.updateEntity(step, stepsResponseDTO);
                todoService.save(todo);
                response = step;
                break;
            }
        }

        if (!stepFound){
            throw new NotFoundException("Step not found in the specified ToDo");
        }

        todoService.updateStatusTodoList(todo);
        return stepsMapper.toDto(response);
    }

    public void validateStep(UUID step){
        if (step == null){
            throw new NotFoundException("Steps not found for todo with id");
        }
    }

    public void deleteSteps (String todoId, List<String> listIdSteps){
        var todo = todoService.validateToDo(todoId);

        for(Step step: todo.getSteps()){
            if (step.getId().equals(listIdSteps)){
                todoService.deleteTodoById(String.valueOf(step));
            }
        }
    }

}
