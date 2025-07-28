package br.com.bodegami.task_manager.application.interceptor;

import br.com.bodegami.task_manager.application.interceptor.dto.FieldError;
import br.com.bodegami.task_manager.application.interceptor.dto.StandardError;
import br.com.bodegami.task_manager.domain.exception.DatabaseException;
import br.com.bodegami.task_manager.domain.exception.DatabaseIntegrityViolation;
import br.com.bodegami.task_manager.domain.exception.UseCaseException;
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
    private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String INVALID_PARAMETERS = "Invalid Parameters";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> handleUserNotFoundException(UserNotFoundException ex,
                                                                     HttpServletRequest request) {
        StandardError response = getError(NOT_FOUND, request.getRequestURI(), ex.getMessage(), null);

        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                              HttpServletRequest request) {

        List<FieldError> listErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        StandardError response = getError(BAD_REQUEST, request.getRequestURI(), INVALID_PARAMETERS, listErrors);

        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DatabaseIntegrityViolation.class)
    public ResponseEntity<StandardError> handleDatabaseIntegrityViolation(DatabaseIntegrityViolation ex, HttpServletRequest request) {
        StandardError response = getError(UNPROCESSABLE_ENTITY, request.getRequestURI(), ex.getMessage(), null);

        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler({DatabaseException.class, UseCaseException.class})
    public ResponseEntity<StandardError> handleDatabaseException(DatabaseException ex, HttpServletRequest request) {
        StandardError response = getError(INTERNAL_SERVER_ERROR, request.getRequestURI(), ex.getMessage(), null);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }

    private StandardError getError(HttpStatus status, String uri, String error, List<FieldError> listErrors) {
        return StandardError.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(status.value())
                .path(uri)
                .error(error)
                .errors(listErrors)
                .build();
    }
}
