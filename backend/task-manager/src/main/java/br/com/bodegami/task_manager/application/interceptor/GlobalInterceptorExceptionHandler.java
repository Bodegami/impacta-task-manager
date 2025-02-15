package br.com.bodegami.task_manager.application.interceptor;

import br.com.bodegami.task_manager.application.interceptor.dto.StandardError;
import br.com.bodegami.task_manager.domain.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalInterceptorExceptionHandler {

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

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

}
