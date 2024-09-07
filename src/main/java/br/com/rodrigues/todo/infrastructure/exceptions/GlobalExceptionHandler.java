package br.com.rodrigues.todo.infrastructure.exceptions;

import br.com.rodrigues.todo.infrastructure.exceptions.custom.BusinessException;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import br.com.rodrigues.todo.infrastructure.exceptions.dto.ErrorResponseDTO;
import br.com.rodrigues.todo.infrastructure.exceptions.dto.ValidateErrorResponseDTO;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> badCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidateErrorResponseDTO>> methodArgumentNotValidException(MethodArgumentNotValidException exception) {

        var errors = exception.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.stream().map(ValidateErrorResponseDTO::new).toList());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> businessException(BusinessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(DataRetrievalFailureException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataRetrievalFailureException(DataRetrievalFailureException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponseDTO> handleOptimisticLockingFailureException(OptimisticLockingFailureException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }


}
