package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class UserController {

    private final UserService userService;

    @Operation(summary = "Creates new User", method ="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully"),
            @ApiResponse(responseCode = "400", description = "Non-standard password or existing user"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Error saving user"),
    })
    @PostMapping
    ResponseEntity<UserResponseDTO> save(@RequestBody @Valid UserRequestDTO dto) {
        var savedUser = userService.saveUser(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.id())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @Operation(summary = "List User", method ="GET")
    @GetMapping
    ResponseEntity<UserResponseDTO> details(JwtAuthenticationToken token) {
        return new ResponseEntity<>(userService.listById(token.getName()), HttpStatus.FOUND);
    }

    @Operation(summary = "Update User", method ="PUT")
    @PutMapping
    ResponseEntity<UserResponseDTO> update(JwtAuthenticationToken token, @RequestBody UserRequestDTO dto) {
        return new ResponseEntity<>(userService.updateUser(token.getName(), dto), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete User", method ="DELETE")
    @DeleteMapping
    ResponseEntity<Void> delete(JwtAuthenticationToken token) {
        userService.deleteUser(token.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
