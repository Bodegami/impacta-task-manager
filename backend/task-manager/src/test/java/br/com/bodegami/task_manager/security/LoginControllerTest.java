package br.com.bodegami.task_manager.security;

import br.com.bodegami.task_manager.application.usecase.AuthenticateUserUseCase;
import br.com.bodegami.task_manager.application.usecase.TestAuthenticationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private AuthenticateUserUseCase authenticateUserUseCase;

    @Mock
    private TestAuthenticationUseCase testAuthenticationUseCase;

    @InjectMocks
    private LoginController loginController;

    private LoginUserDTO loginUserDto;
    private RecoveryJwtTokenDTO recoveryJwtTokenDTO;

    @BeforeEach
    void setUp() {
        loginUserDto = new LoginUserDTO("test@example.com", "password123");
        recoveryJwtTokenDTO = new RecoveryJwtTokenDTO("test-token");
    }

    @Test
    void authenticateUser_ShouldReturnToken_WhenAuthenticationIsSuccessful() {
        // Arrange
        when(authenticateUserUseCase.execute(loginUserDto)).thenReturn(recoveryJwtTokenDTO);

        // Act
        ResponseEntity<RecoveryJwtTokenDTO> response = loginController.authenticateUser(loginUserDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recoveryJwtTokenDTO.token(), response.getBody().token());
        verify(authenticateUserUseCase, times(1)).execute(loginUserDto);
    }

    @Test
    void getAuthenticationTest_ShouldReturnDefaultMessage() {
        // Arrange
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Autenticado com sucesso");
        when(testAuthenticationUseCase.execute(null)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = loginController.getAuthenticationTest();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Autenticado com sucesso", response.getBody());
        verify(testAuthenticationUseCase, times(1)).execute(null);
    }

    @Test
    void getCustomerAuthenticationTest_ShouldReturnCustomerMessage() {
        // Arrange
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Cliente autenticado com sucesso");
        when(testAuthenticationUseCase.execute("customer")).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = loginController.getCustomerAuthenticationTest();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente autenticado com sucesso", response.getBody());
        verify(testAuthenticationUseCase, times(1)).execute("customer");
    }

    @Test
    void getAdminAuthenticationTest_ShouldReturnAdminMessage() {
        // Arrange
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Administrador autenticado com sucesso");
        when(testAuthenticationUseCase.execute("administrator")).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = loginController.getAdminAuthenticationTest();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Administrador autenticado com sucesso", response.getBody());
        verify(testAuthenticationUseCase, times(1)).execute("administrator");
    }
}
