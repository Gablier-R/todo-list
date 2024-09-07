package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.auth.LoginRequestDTO;
import br.com.rodrigues.todo.api.dto.auth.LoginResponseDTO;
import br.com.rodrigues.todo.domain.services.AuthService;
import br.com.rodrigues.todo.domain.services.UserService;
import br.com.rodrigues.todo.infrastructure.exceptions.dto.ErrorResponseDTO;
import org.apache.juli.logging.Log;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    static final LoginResponseDTO LOGIN_RESPONSE_SUCCESS = new LoginResponseDTO("token", 100L);

    @Test
    void login_WithValidValues_ReturnsSuccess() {

        when(authService.authUser(any())).thenReturn(LOGIN_RESPONSE_SUCCESS);

        ResponseEntity<LoginResponseDTO> sut = authController.login(any());
        System.out.println("SUT: " + sut);

        assertThat(sut).as("Sut can't be null").isNotNull();
        assertThat(Objects.requireNonNull(sut.getBody()).accessToken()).isEqualTo(LOGIN_RESPONSE_SUCCESS.accessToken());
        assertThat(sut.getBody().expiresIn()).isEqualTo(LOGIN_RESPONSE_SUCCESS.expiresIn());
    }

}