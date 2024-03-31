package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.domain.services.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/toDo")
public class ToDoListController {

    private final TodoService toDoService;

    @PostMapping
    ResponseEntity<ToDoResponseDTO> saveToDo(@RequestBody @Valid ToDoRequestDTO todo) {
        var savedToDo = toDoService.saveToDo(todo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedToDo.id())
                .toUri();
        return ResponseEntity.created(location).body(savedToDo);
    }

    @GetMapping("/{id}")
    ResponseEntity<ToDoResponseDTO> detailsToDo(@PathVariable String id){
        return new ResponseEntity<>(toDoService.detailsToDo(id), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<ToDoResponseDTO>> listAllToDo (){
        return new ResponseEntity<>(toDoService.findAllToDo(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<ToDoResponseDTO> updateToDo (@PathVariable String id, @RequestBody ToDoRequestDTO dto){
        return new ResponseEntity<>(toDoService.updateToDo(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTodo(@PathVariable String id){
        toDoService.deleteTodoById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
