package br.com.rodrigues.todo.api.dto.auth;

public record LoginResponseDTO(
        String accessToken,
        Long expiresIn
) {
}
