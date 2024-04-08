package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.user.UserRequestDTO;
import br.com.rodrigues.todo.domain.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void saveUser() {
        UserRequestDTO user = new UserRequestDTO("Testando", "da Silva", "testando@gmail.com", LocalDate.of(2000, 4, 8));
        var userSaved = userService.saveUser(user);
        assertNotNull(userSaved);
    }

    @Test
    void updateUser() {
    }

    @Test
    void listById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void validateUser() {
    }
}