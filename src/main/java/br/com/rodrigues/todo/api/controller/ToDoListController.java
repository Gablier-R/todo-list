package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.services.ToDoListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static br.com.rodrigues.todo.utils.Constants.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/toDo")
public class ToDoListController {

    private final ToDoListService toDoService;

    @PostMapping("/{userId}")
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
    ResponseEntity<PageableDTO> listAllToDoByUser(@PathVariable String userId,
                                                  @RequestParam(required = false) String priority,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                  @RequestParam(defaultValue = DEFAULT_SORT, required = false) String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return new ResponseEntity<>(toDoService.findAllToDo(userId, pageable), HttpStatus.OK);
    }

    @GetMapping("/filter/{userId}")
    ResponseEntity<PageableDTO> listAllToDoByUserPriority(@PathVariable String userId,
                                                          @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                          @RequestParam String priority) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return new ResponseEntity<>(toDoService.findAllToDoFilterCustom(userId, priority, pageable), HttpStatus.OK);
    }


    @GetMapping("/{userId}/{todoId}")
    ResponseEntity<ToDoResponseDTO> detailsToDo(@PathVariable String userId, @PathVariable String todoId) {
        return new ResponseEntity<>(toDoService.detailsToDo(userId, todoId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/{todoId}")
    ResponseEntity<ToDoResponseDTO> updateToDo(@PathVariable String userId, @PathVariable String todoId, @RequestBody ToDoRequestDTO dto) {
        return new ResponseEntity<>(toDoService.updateToDo(userId, todoId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}/{todoId}")
    ResponseEntity<Void> deleteTodo(@PathVariable String userId, @PathVariable String todoId) {
        toDoService.deleteTodoByUserIdAndTodoId(userId, todoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
