package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.StringListDTO;
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

    @PatchMapping("/{userId}/{todoId}")
    ResponseEntity<List<StepsResponseDTO>> saveSteps (@PathVariable String userId, @PathVariable String todoId, @RequestBody List<StepsRequestDTO>  dto){
        return new ResponseEntity<>(stepsService.saveStep(userId, todoId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/{todoId}")
    ResponseEntity<List<StepsResponseDTO>> listStepsByTodoId (@PathVariable String userId, @PathVariable String todoId){
        return new ResponseEntity<>(stepsService.listStepsByTodo(userId, todoId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/{todoId}/{stepId}")
    ResponseEntity<StepsResponseDTO> findByStepByTodoIdAndStepId (@PathVariable String userId, @PathVariable String todoId, @PathVariable String stepId){
        return new ResponseEntity<>(stepsService.findStepByTodoIdAndStepId(userId, todoId, stepId), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/{todoId}/{stepId}")
    ResponseEntity<StepsResponseDTO> updateStep (@PathVariable String userId, @PathVariable String todoId, @PathVariable String stepId, @RequestBody StepsRequestDTO dto){
        return new ResponseEntity<>(stepsService.updateStep(userId ,todoId,stepId,dto), HttpStatus.OK);
    }

    @PatchMapping("/delete/{userId}/{todoId}/{stepId}")
    ResponseEntity<Void> delete (@PathVariable String userId, @PathVariable String todoId, @PathVariable String stepId){
        stepsService.deleteSteps(userId, todoId, stepId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
