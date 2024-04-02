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

    @PutMapping("/{userId}")
    ResponseEntity<ToDoResponseDTO> saveToDo(@PathVariable String userId, @RequestBody @Valid ToDoRequestDTO todo) {
        var savedToDo = toDoService.saveToDo(userId, todo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedToDo.id())
                .toUri();
        return ResponseEntity.created(location).body(savedToDo);
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<ToDoResponseDTO>> listAllToDoByUser (@PathVariable String userId){
        return new ResponseEntity<>(toDoService.findAllToDo(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/{todoId}")
    ResponseEntity<ToDoResponseDTO> detailsToDo(@PathVariable String userId,  @PathVariable String todoId){
        return new ResponseEntity<>(toDoService.detailsToDo(userId, todoId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/{todoId}")
    ResponseEntity<ToDoResponseDTO> updateToDo (@PathVariable String userId, @PathVariable String todoId, @RequestBody ToDoRequestDTO dto){
        return new ResponseEntity<>(toDoService.updateToDo(userId, todoId, dto), HttpStatus.OK);
    }

    @PutMapping("/delete/{userId}/{todoId}")
    ResponseEntity<Void> deleteTodo(@PathVariable String userId,  @PathVariable String todoId){
        toDoService.deleteTodoByUserIdAndTodoId(userId, todoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
