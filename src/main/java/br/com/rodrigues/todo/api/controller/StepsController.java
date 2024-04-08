package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.services.StepsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.rodrigues.todo.utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.rodrigues.todo.utils.Constants.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor

@RestController
@RequestMapping("/step")
public class StepsController {

    private final StepsService stepsService;

//    @PostMapping("/{userId}/{todoId}")
//    ResponseEntity<List<StepsResponseDTO>> saveSteps(@PathVariable String userId, @PathVariable String todoId, @RequestBody List<StepsRequestDTO> dto) {
//        var saveStep = stepsService.saveStep(userId, todoId, dto);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(saveStep)
//                .toUri();
//        return ResponseEntity.created(location).body(saveStep);
//    }

    @PostMapping("/{userId}/{todoId}")
    ResponseEntity<List<StepsResponseDTO>> saveSteps(@PathVariable String userId, @PathVariable String todoId, @RequestBody List<StepsRequestDTO> dto) {
        return new ResponseEntity<>(stepsService.saveStepBy(userId, todoId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/{todoId}")
    ResponseEntity<PageableDTO> listStepsByTodoId(@PathVariable String userId, @PathVariable String todoId,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return new ResponseEntity<>(stepsService.listStepsBy(userId, todoId, pageable), HttpStatus.OK);
    }

    @GetMapping("/{userId}/{todoId}/{stepId}")
    ResponseEntity<StepsResponseDTO> findByStepByTodoIdAndStepId(@PathVariable String userId, @PathVariable String todoId, @PathVariable String stepId) {
        return new ResponseEntity<>(stepsService.findUniqueStepBy(userId, todoId, stepId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/{todoId}/{stepId}")
    ResponseEntity<StepsResponseDTO> updateStep(@PathVariable String userId, @PathVariable String todoId, @PathVariable String stepId, @RequestBody StepsRequestDTO dto) {
        return new ResponseEntity<>(stepsService.updateStepBy(userId, todoId, stepId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}/{todoId}/{stepId}")
    ResponseEntity<Void> delete(@PathVariable String userId, @PathVariable String todoId, @PathVariable String stepId) {
        stepsService.deleteStepsBy(userId, todoId, stepId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
