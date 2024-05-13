package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor

@RestController
@RequestMapping("/user")
@Tag(name = "user")
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
    @GetMapping
    ResponseEntity<UserResponseDTO> listUserById(JwtAuthenticationToken token) {
        return new ResponseEntity<>(userService.listById(token.getName()), HttpStatus.FOUND);
    }

    @Operation(summary = "Atualizar usuario", method ="PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Erro ao atualizar usuario"),
    })
    @PutMapping
    ResponseEntity<UserResponseDTO> updateUser(JwtAuthenticationToken token, @RequestBody UserRequestDTO dto) {
        return new ResponseEntity<>(userService.updateUser(token.getName(), dto), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Deletar usuario", method ="DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Erro ao deletar usuario"),
    })
    @DeleteMapping
    ResponseEntity<Void> deleteById(JwtAuthenticationToken token) {
        userService.deleteUser(token.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
