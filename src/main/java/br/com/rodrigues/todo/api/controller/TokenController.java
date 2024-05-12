package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.auth.LoginRequestDTO;
import br.com.rodrigues.todo.api.dto.auth.LoginResponseDTO;
import br.com.rodrigues.todo.domain.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping("/login")
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final AuthService authService;

    @PostMapping
    ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO requestDTO){

        return new ResponseEntity<>(authService.authUser(requestDTO), HttpStatus.OK);

    }
}
