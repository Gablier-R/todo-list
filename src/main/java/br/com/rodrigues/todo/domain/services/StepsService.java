package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.StringListDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.entities.User;
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

    private final UserService userService;
    private final TodoService todoService;
    private final StepsMapper stepsMapper;


    public List<StepsResponseDTO> saveStep(String userId, String todoId, List<StepsRequestDTO> dto) {

        User user = userService.validateUser(userId);

        var todo = todoService.validateListInUser(user, todoId);

        var entity = stepsMapper.toListEntity(dto);

        List<Step> stepList = new ArrayList<>(entity);

        todo.getSteps().addAll(stepList);

        userService.save(user);

        return stepsMapper.toListDto(todo.getSteps());
    }

    public List<StepsResponseDTO> listStepsByTodo(String userId, String todoId) {

        User user = userService.validateUser(userId);

        var todo = todoService.validateListInUser(user, todoId);

        return stepsMapper.toListDto(todo.getSteps());
    }

    public StepsResponseDTO findStepByTodoIdAndStepId(String userId, String todoId, String stepId) {

        User user = userService.validateUser(userId);

        var todo = todoService.validateListInUser(user, todoId);

        for (Step step : todo.getSteps()) {
            if (step.getId().equals(stepId)) {
                return stepsMapper.toDto(step);
            }
        }
        throw new NotFoundException("Step not found in the specified ToDo");
    }

    public StepsResponseDTO updateStep(String userId, String todoId, String stepId, StepsRequestDTO stepsResponseDTO) {
        User user = userService.validateUser(userId);

        var todo = todoService.validateListInUser(user, todoId);

        for (Step step : todo.getSteps()) {
            if (step.getId().equals(stepId)) {

                var stepUpdated = stepsMapper.updateEntity(step, stepsResponseDTO);
                todoService.updateStatusTodoList(todo);
                userService.save(user);
                return stepsMapper.toDto(stepUpdated);

            }
        }
        throw new NotFoundException("Step not found in the specified ToDo");
    }

    public void deleteSteps(String userId, String todoId, String stepId) {

        User user = userService.validateUser(userId);

        var todo = todoService.validateListInUser(user, todoId);

            for (Step step : todo.getSteps()) {
                if (!step.getId().equals(stepId)) {
                    throw new NotFoundException("Step not found in the specified ToDo");
                }
                todo.getSteps().remove(step);
                userService.save(user);
            }

    }
}
