package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.services.StepsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/step")
public class StepsController {

    private final StepsService stepsService;

    @PostMapping("/{todoId}")
    ResponseEntity<List<StepsResponseDTO>> saveSteps (@PathVariable String todoId, @RequestBody List<StepsRequestDTO>  dto){
        return new ResponseEntity<>(stepsService.saveStep(todoId,dto), HttpStatus.CREATED);
    }

    @GetMapping("/{todoId}")
    ResponseEntity<List<StepsResponseDTO>> listStepsByTodoId (@PathVariable String todoId){
        return new ResponseEntity<>(stepsService.listStepsByTodo(todoId), HttpStatus.OK);
    }

    @GetMapping("/{todoId}/{stepId}")
    ResponseEntity<StepsResponseDTO> findByStepByTodoIdAndStepId (@PathVariable String todoId, @PathVariable String stepId){
        return new ResponseEntity<>(stepsService.findStepByTodoIdAndStepId(todoId,stepId), HttpStatus.OK);
    }

    @PutMapping("/{todoId}/{stepId}")
    ResponseEntity<StepsResponseDTO> updateStep (@PathVariable String todoId, @PathVariable String stepId, @RequestBody StepsRequestDTO dto){
        return new ResponseEntity<>(stepsService.updateStep(todoId,stepId,dto), HttpStatus.OK);
    }

    @PostMapping("/delete/{todoId}")
    ResponseEntity<Void> delete (@PathVariable String todoId, @RequestBody List<String> listSteps){
        stepsService.deleteSteps(todoId, listSteps);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
