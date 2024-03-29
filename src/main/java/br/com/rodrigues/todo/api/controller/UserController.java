package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.api.dto.user.UserResponseDTO;
import br.com.rodrigues.todo.domain.repositories.UserRepository;
import br.com.rodrigues.todo.domain.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<UserResponseDTO> saveUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        var savedUser = userService.saveUser(userRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.id())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }
}
