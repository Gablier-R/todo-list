package br.com.rodrigues.todo.api.dto.auth;

public record LoginRequestDTO(
        String email,
        String password
) {
}
