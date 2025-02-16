package br.com.bodegami.task_manager.application.interceptor;

import br.com.bodegami.task_manager.application.interceptor.dto.FieldError;
import br.com.bodegami.task_manager.application.interceptor.dto.StandardError;
import br.com.bodegami.task_manager.domain.exception.DatabaseIntegrityViolation;
import br.com.bodegami.task_manager.domain.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalInterceptorExceptionHandler {

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus UNPROCESSABLE_ENTITY = HttpStatus.UNPROCESSABLE_ENTITY;
    private static final String INVALID_PARAMETERS = "Invalid Parameters";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> handleUserNotFoundException(UserNotFoundException ex,
                                                                     HttpServletRequest request) {
        StandardError response = StandardError.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(NOT_FOUND.value())
                .path(request.getRequestURI())
                .error(ex.getMessage())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                              HttpServletRequest request) {

        List<FieldError> listErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        StandardError response = StandardError.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(BAD_REQUEST.value())
                .path(request.getRequestURI())
                .error(INVALID_PARAMETERS)
                .errors(listErrors)
                .build();

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DatabaseIntegrityViolation.class)
    public ResponseEntity<StandardError> handleDatabaseIntegrityViolation(DatabaseIntegrityViolation ex,
                                                                          HttpServletRequest request) {
        StandardError response = StandardError.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(UNPROCESSABLE_ENTITY.value())
                .path(request.getRequestURI())
                .error(ex.getMessage())
                .build();

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(response);
    }

}
