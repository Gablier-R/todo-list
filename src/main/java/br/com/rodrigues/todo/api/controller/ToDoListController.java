package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.services.ToDoListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static br.com.rodrigues.todo.utils.Constants.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/toDo")
@Tag(name = "toDo")
public class ToDoListController {

    private final ToDoListService toDoService;

    @PostMapping
    ResponseEntity<ToDoResponseDTO> saveToDo(JwtAuthenticationToken token, @RequestBody @Valid ToDoRequestDTO todo) {
        var savedToDo = toDoService.saveToDoBy(token.getName(), todo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(token.getName()+"/"+savedToDo.id())
                .toUri();
        return ResponseEntity.created(location).body(savedToDo);
    }

    @GetMapping
    ResponseEntity<PageableDTO> listAllToDoByUser(JwtAuthenticationToken token,
                                                  @RequestParam(defaultValue = DEFAULT_STATUS, required = false) boolean status,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                  @RequestParam(defaultValue = DEFAULT_SORT, required = false) String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return new ResponseEntity<>(toDoService.findAllToDoBy(token.getName(), pageable, String.valueOf(status)), HttpStatus.OK);
    }

    @GetMapping("/filter")
    ResponseEntity<PageableDTO> listAllToDoByUserPriority(JwtAuthenticationToken token,
                                                          @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                          @RequestParam String priority) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return new ResponseEntity<>(toDoService.findAllToDoFilterCustomBy(token.getName(), priority, pageable), HttpStatus.OK);
    }


    @GetMapping("/{todoId}")
    ResponseEntity<ToDoResponseDTO> detailsToDo(JwtAuthenticationToken token, @PathVariable String todoId) {
        return new ResponseEntity<>(toDoService.detailsToDoBy(token.getName(), todoId), HttpStatus.OK);
    }

    @PutMapping("/{todoId}")
    ResponseEntity<ToDoResponseDTO> updateToDo(JwtAuthenticationToken token, @PathVariable String todoId, @RequestBody ToDoRequestDTO dto) {
        return new ResponseEntity<>(toDoService.updateToDoBy(token.getName(), todoId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}")
    ResponseEntity<Void> deleteTodo(JwtAuthenticationToken token, @PathVariable String todoId) {
        toDoService.deleteToDoBy(token.getName(), todoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
