package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.repositories.StepRepository;
import br.com.rodrigues.todo.domain.services.map.MapPage;
import br.com.rodrigues.todo.domain.services.map.StepsMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.BusinessException;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class StepsService {

    private final StepRepository stepRepository;
    private final ToDoListService toDoListService;
    private final StepsMapper stepsMapper;
    private final MapPage mapPage;


    public List<StepsResponseDTO> saveStepBy(String userId, String todoId, List<StepsRequestDTO> dto) {

        toDoListService.validateToDoList(todoId, userId);

        List<Step> stepList = new ArrayList<>(stepsMapper.toListEntity(todoId, dto));

        return stepsMapper.toListDto(stepRepository.saveAll(stepList));
    }

    public PageableDTO listStepsBy(String userId, String todoId, Pageable pageable) {

        toDoListService.validateToDoList(todoId, userId);

        Page<Step> page = stepRepository.findAllByToDoListId(todoId, pageable);

        var response = stepsMapper.toListDto(page.getContent());

        return mapPage.mapToResponseAll(response, page);
    }

    public StepsResponseDTO updateStepBy(String userId, String todoId, String stepId, StepsRequestDTO stepsResponseDTO) {

        var todo = toDoListService.validateToDoList(todoId, userId);

        var validatedStep = validatedStepBy(userId,todoId, stepId);

        var entityUpdated = stepsMapper.updateEntity(validatedStep, stepsResponseDTO);

        var response = stepRepository.save(entityUpdated);

        updateStepsIsDone(todo);

        return stepsMapper.toDto(response);
    }

    public void updateStepsIsDone(ToDoList toDoList) {

        List<Step> stepResponse = new ArrayList<>();
        if (toDoList.getIsDone().equals(true)) {
            for (Step step : toDoList.getSteps()) {
                step.setIsDone(true);
                stepResponse.add(step);
            }
        }

        stepRepository.saveAll(stepResponse);
    }

    public StepsResponseDTO findUniqueStepBy(String userId, String todoId, String stepId) {

        return stepsMapper.toDto(validatedStepBy(userId, todoId, stepId));
    }

    public void deleteStepsBy(String userId, String todoId, String stepId) {

        validatedStepBy(userId,todoId, stepId);

        stepRepository.deleteById(stepId);
    }

    private Step validatedStepBy(String userId, String todoId, String stepId) {

        var todo = toDoListService.validateToDoList(todoId, userId);
        var step = stepRepository.findById(stepId).orElseThrow(
                () -> new NotFoundException("Step not found in the specified ToDo"));

        if (todo.getId().equals(step.getToDoListId())){
            throw new BusinessException("This step does not correspond to that specific list");
        }

        return step;
    }
}
