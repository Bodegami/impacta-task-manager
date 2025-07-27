package br.com.bodegami.task_manager.application.usecase.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestAuthenticationUseCaseImplTest {

    @InjectMocks
    private TestAuthenticationUseCaseImpl testAuthenticationUseCase;

    @Test
    void execute_ShouldThrownException_WhenUserTypeProvidedIsNull() {
        // Act
        String expectedMessage = "Invalid user type";

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> testAuthenticationUseCase.execute(null));
        // Assert
        assertNotNull(result);
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void execute_ShouldReturnDefaultMessage_WhenNoUserTypeProvided() {
        // Act

        ResponseEntity<String> response = testAuthenticationUseCase.execute("");
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Autenticado com sucesso", response.getBody());
    }

    @Test
    void execute_ShouldReturnCustomerMessage_WhenCustomerTypeProvided() {
        // Act
        ResponseEntity<String> response = testAuthenticationUseCase.execute("customer");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente autenticado com sucesso", response.getBody());
    }

    @Test
    void execute_ShouldReturnAdminMessage_WhenAdminTypeProvided() {
        // Act
        ResponseEntity<String> response = testAuthenticationUseCase.execute("administrator");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Administrador autenticado com sucesso", response.getBody());
    }
}
