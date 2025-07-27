package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.usecase.TestAuthenticationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TestAuthenticationUseCaseImpl implements TestAuthenticationUseCase {

    private static final String USER_TYPE_CUSTOMER = "customer";
    private static final String USER_TYPE_ADMINISTRATOR = "administrator";
    private static final String INVALID_USER_TYPE_USER = "Invalid user type";

    @Override
    public ResponseEntity<String> execute(String userType) {
        try {
            String message = switch (userType) {
                case USER_TYPE_CUSTOMER -> "Cliente autenticado com sucesso";
                case USER_TYPE_ADMINISTRATOR -> "Administrador autenticado com sucesso";
                default -> "Autenticado com sucesso"; //TODO: Should throw an exception?
            };
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(INVALID_USER_TYPE_USER, e);
        }
    }
}
