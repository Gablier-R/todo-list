package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.services.map.StepsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class StepsService {

    private final TodoService todoService;
    private final StepsMapper stepsMapper;


    public List<StepsResponseDTO> saveStep (String id, List<StepsRequestDTO>  dto){

        var todo = todoService.findById(id);

        var entity = stepsMapper.toListEntity(dto);

        List<Step> stepList = new ArrayList<>(entity);

        todo.getSteps().addAll(stepList);

        todoService.save(todo);

        return stepsMapper.toListDto(todo.getSteps());
    }

    public List<StepsResponseDTO> listStepsByTodo (String todoId) {
        var todo = todoService.findById(todoId);

        var steps = todo.getSteps();

        return stepsMapper.toListDto(steps);
    }

    public StepsResponseDTO findStepByTodoIdAndStepId(String todoId, String stepId){
        var todo = todoService.findById(todoId);

        var steps = todo.getSteps();

        for (Step step: steps){
            if (step.getId().equals(stepId)){
                return stepsMapper.toDto(step);
            }
        }
        return null;
    }

    public StepsResponseDTO updateStep (String todoId, String stepId, StepsRequestDTO stepsResponseDTO){
        var todo = todoService.findById(todoId);

        Step response = null;

        for (Step step: todo.getSteps()){
            if (step.getId().equals(stepId)){
                stepsMapper.updateEntity(step, stepsResponseDTO);
                todoService.save(todo);

                response = step;
            }
        }

        todoService.updateStatusTodoList(todo);
        return stepsMapper.toDto(response);
    }

    public void deleteSteps (String todoId, List<String> listIdSteps){
        var todo = todoService.findById(todoId);

        for(Step step: todo.getSteps()){
            if (step.getId().equals(listIdSteps)){
                todoService.deleteTodoById(String.valueOf(step));
            }
        }
    }

}
