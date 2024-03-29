package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.domain.entities.Steps;
import br.com.rodrigues.todo.domain.services.StepsService;
import br.com.rodrigues.todo.domain.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/steps")
public class StepsController {

    private final StepsService stepsService;

    @PostMapping("/{todoId}")
    ResponseEntity<List<StepsResponseDTO>> saveSteps (@PathVariable String todoId, @RequestBody List<StepsRequestDTO>  dto){

        return new ResponseEntity<>(stepsService.save(todoId,dto), HttpStatus.CREATED);
    }
}
