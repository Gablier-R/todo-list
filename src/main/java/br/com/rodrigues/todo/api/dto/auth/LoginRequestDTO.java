package br.com.rodrigues.todo.api.dto.auth;

public record LoginRequestDTO(
        String username,
        String password
) {
}
