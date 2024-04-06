package br.com.rodrigues.todo.infrastructure.exceptions.dto;

import org.springframework.validation.FieldError;

public record ValidateErrorResponseDTO(
        String field,
        String message
) {

    public ValidateErrorResponseDTO(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
