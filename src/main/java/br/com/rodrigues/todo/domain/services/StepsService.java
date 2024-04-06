package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.entities.Step;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.repositories.StepRepository;
import br.com.rodrigues.todo.domain.services.map.MapPage;
import br.com.rodrigues.todo.domain.services.map.StepsMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class StepsService {

    private final UserService userService;
    private final StepRepository stepRepository;
    private final ToDoListService toDoListService;
    private final StepsMapper stepsMapper;
    private final MapPage mapPage;


    public List<StepsResponseDTO> saveStep(String userId, String todoId, List<StepsRequestDTO> dto) {

        userService.validateUser(userId);

        toDoListService.validateToDoList(todoId);

        var entity = stepsMapper.toListEntity(todoId, dto);

        List<Step> stepList = new ArrayList<>(entity);

        var response = stepRepository.saveAll(stepList);

        return stepsMapper.toListDto(response);
    }

    public PageableDTO listStepsByTodo(String userId, String todoId, Pageable pageable) {

        userService.validateUser(userId);

        toDoListService.validateToDoList(todoId);

        Page<Step> page = stepRepository.findAllByToDoListId(todoId, pageable);


        var response = stepsMapper.toListDto(page.getContent());

        return mapPage.mapToResponseAll(response, page);
    }

    public StepsResponseDTO updateStep(String userId, String toDoListId, String stepId, StepsRequestDTO stepsResponseDTO) {

        userService.validateUser(userId);

        var todo = toDoListService.validateToDoList(toDoListId);

        var validatedStep = validatedStep(stepId);

        var entityUpdated = stepsMapper.updateEntity(validatedStep, stepsResponseDTO);

        var response = stepRepository.save(entityUpdated);

        updateStatusStepsIsDone(todo);

        return stepsMapper.toDto(response);
    }

    public void updateStatusStepsIsDone(ToDoList toDoList) {

        List<Step> stepResponse = new ArrayList<>();
        if (toDoList.getIsDone().equals(true)) {
            for (Step step : toDoList.getSteps()) {
                step.setIsDone(true);
                stepResponse.add(step);
            }
        }

        stepRepository.saveAll(stepResponse);
    }

    public StepsResponseDTO findStepByTodoIdAndStepId(String userId, String toDoListId, String stepId) {

        userService.validateUser(userId);

        toDoListService.validateToDoList(toDoListId);

        var response = validatedStep(stepId);
        return stepsMapper.toDto(response);
    }

    public void deleteSteps(String userId, String todoId, String stepId) {

        userService.validateUser(userId);

        toDoListService.validateToDoList(todoId);

        validatedStep(stepId);

        stepRepository.deleteById(stepId);
    }

    private Step validatedStep(String id) {
        return stepRepository.findById(id).orElseThrow(() -> new NotFoundException("Step not found in the specified ToDo"));
    }


}

//public StepsResponseDTO updateStep(String stepId, StepsRequestDTO stepsResponseDTO) {
//
//        var response = validatedStep(stepId);
//
//
//        for (Step step : todo.getSteps()) {
//            if (step.getId().equals(stepId)) {
//                Step stepUpdated = stepsMapper.updateEntity(step, stepsResponseDTO);
//
//                // Verifica se todos os passos estão concluídos
//                boolean allStepsDone = todo.getSteps().stream().allMatch(Step::getIsDone);
//                if (allStepsDone) {
//                    todo.setIsDone(true);
//                }
//
//                // Atualiza a lista de tarefas do usuário com a ToDoList atualizada
//                user.getList().stream()
//                        .filter(list -> list.getId().equals(todoId))
//                        .findFirst()
//                        .ifPresent(list -> list.setIsDone(todo.getIsDone()));
//
//                // Salva as alterações no usuário
//                userService.save(user);
//
//                // Retorna o passo atualizado
//                return stepsMapper.toDto(stepUpdated);
//            }
//        }
//        throw new NotFoundException("Step not found in the specified ToDo");
//    }