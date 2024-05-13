package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.steps.StepsRequestDTO;
import br.com.rodrigues.todo.api.dto.steps.StepsResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.services.StepsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static br.com.rodrigues.todo.utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.rodrigues.todo.utils.Constants.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor

@RestController
@RequestMapping("/step")
@Tag(name = "step")
public class StepsController {

    private final StepsService stepsService;

    @PostMapping("/{todoId}")
    ResponseEntity<List<StepsResponseDTO>> saveSteps(JwtAuthenticationToken token, @PathVariable String todoId, @RequestBody List<StepsRequestDTO> dto) {
        var saveStep = stepsService.saveStepBy(token.getName(), todoId, dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveStep)
                .toUri();
        return ResponseEntity.created(location).body(saveStep);
    }

//    @PostMapping("/{userId}/{todoId}")
//    ResponseEntity<List<StepsResponseDTO>> saveSteps(@PathVariable String userId, @PathVariable String todoId, @RequestBody List<StepsRequestDTO> dto) {
//        return new ResponseEntity<>(stepsService.saveStepBy(userId, todoId, dto), HttpStatus.CREATED);
//    }
//
    @GetMapping("/{todoId}")
    ResponseEntity<PageableDTO> listStepsByTodoId(JwtAuthenticationToken token, @PathVariable String todoId,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return new ResponseEntity<>(stepsService.listStepsBy(token.getName(), todoId, pageable), HttpStatus.OK);
    }

    @GetMapping("/{todoId}/{stepId}")
    ResponseEntity<StepsResponseDTO> findByStepByTodoIdAndStepId(JwtAuthenticationToken token, @PathVariable String todoId, @PathVariable String stepId) {
        return new ResponseEntity<>(stepsService.findUniqueStepBy(token.getName(), todoId, stepId), HttpStatus.OK);
    }

    @PutMapping("/{todoId}/{stepId}")
    ResponseEntity<StepsResponseDTO> updateStep(JwtAuthenticationToken token, @PathVariable String todoId, @PathVariable String stepId, @RequestBody StepsRequestDTO dto) {
        return new ResponseEntity<>(stepsService.updateStepBy(token.getName(), todoId, stepId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}/{stepId}")
    ResponseEntity<Void> delete(JwtAuthenticationToken token, @PathVariable String todoId, @PathVariable String stepId) {
        stepsService.deleteStepsBy(token.getName(), todoId, stepId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
