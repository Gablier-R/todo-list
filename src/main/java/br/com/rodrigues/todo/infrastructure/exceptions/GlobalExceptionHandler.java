package br.com.rodrigues.todo.infrastructure.exceptions;

import br.com.rodrigues.todo.infrastructure.exceptions.custom.BusinessException;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import br.com.rodrigues.todo.infrastructure.exceptions.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFoundException (NotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDTO>> methodArgumentNotValidException (MethodArgumentNotValidException exception){

         var errors = exception.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(ErrorResponseDTO::new).toList());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> businessException (BusinessException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResponseDTO(exception.getMessage()));
    }


}
