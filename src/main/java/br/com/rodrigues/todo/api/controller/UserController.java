package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.todo.ToDoRequestDTO;
import br.com.rodrigues.todo.api.dto.todo.ToDoResponseDTO;
import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.domain.entities.ToDoList;
import br.com.rodrigues.todo.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Salvar usuario", method ="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario savo com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao salvar usuario"),
    })
    @PostMapping
    ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserRequestDTO dto) {
        var savedUser = userService.saveUser(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.id())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }


    @Operation(summary = "Listar usario", method ="GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Erro ao buscar usuario"),
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> listUserById(@PathVariable String id) {
        return new ResponseEntity<>(userService.listById(id), HttpStatus.FOUND);
    }

    @Operation(summary = "Atualizar usuario", method ="PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Erro ao atualizar usuario"),
    })
    @PutMapping("/{id}")
    ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, @RequestBody UserRequestDTO dto) {
        return new ResponseEntity<>(userService.updateUser(id, dto), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deletar usuario", method ="DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Erro ao deletar usuario"),
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
