package br.com.bodegami.task_manager.application.usecase;

import org.springframework.http.ResponseEntity;

public interface TestAuthenticationUseCase {
    ResponseEntity<String> execute(String userType);
}
