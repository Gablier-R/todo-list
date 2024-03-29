package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.todo.TodoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.TodoResponseDTO;
import br.com.rodrigues.todo.domain.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDTO> saveTodo( @RequestBody @Valid TodoRequestDTO todo) {
        var savedTodo = todoService.saveTodo(todo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTodo.id())
                .toUri();
        return ResponseEntity.created(location).body(savedTodo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> detailsTodo(@PathVariable String id){
        return new ResponseEntity<>(todoService.detailsTodo(id), HttpStatus.OK);
    }



}
