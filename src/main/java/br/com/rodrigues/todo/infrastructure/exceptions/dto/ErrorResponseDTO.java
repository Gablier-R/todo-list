package br.com.rodrigues.todo.infrastructure.exceptions.dto;

import org.springframework.validation.FieldError;

public record ErrorResponseDTO(
        String field,
        String message
) {

    public ErrorResponseDTO (FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }

    public ErrorResponseDTO (String message){
        this(null, message);
    }
}
