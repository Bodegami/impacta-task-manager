package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import br.com.bodegami.task_manager.security.LoginUserDTO;
import br.com.bodegami.task_manager.security.RecoveryJwtTokenDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private AuthenticateUserUseCaseImpl authenticateUserUseCase;

    private final LoginUserDTO loginUserDto = new LoginUserDTO("test@example.com", "password123");
    private final RecoveryJwtTokenDTO expectedToken = new RecoveryJwtTokenDTO("test-token");

    @Test
    void execute_ShouldReturnToken_WhenAuthenticationIsSuccessful() {
        // Arrange
        when(userService.authenticateUser(loginUserDto)).thenReturn(expectedToken);

        // Act
        RecoveryJwtTokenDTO result = authenticateUserUseCase.execute(loginUserDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedToken.token(), result.token());
        verify(userService, times(1)).authenticateUser(loginUserDto);
    }

    @Test
    void execute_ShouldCallUserServiceWithCorrectParameters() {
        // Arrange
        when(userService.authenticateUser(loginUserDto)).thenReturn(expectedToken);

        // Act
        RecoveryJwtTokenDTO result = authenticateUserUseCase.execute(loginUserDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedToken.token(), result.token());
        verify(userService, times(1)).authenticateUser(loginUserDto);
    }
}
