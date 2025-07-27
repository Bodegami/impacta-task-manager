package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.domain.service.UserService;
import br.com.bodegami.task_manager.security.LoginUserDTO;
import br.com.bodegami.task_manager.security.RecoveryJwtTokenDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticateUserUseCaseImpl authenticateUserUseCase;

    private LoginUserDTO loginUserDto;
    private RecoveryJwtTokenDTO expectedToken;

    @BeforeEach
    void setUp() {
        loginUserDto = new LoginUserDTO("test@example.com", "password123");
        expectedToken = new RecoveryJwtTokenDTO("test-token");
    }

    @Test
    void execute_ShouldReturnToken_WhenAuthenticationIsSuccessful() {
        // Arrange
        when(userService.authenticateUser(any(LoginUserDTO.class))).thenReturn(expectedToken);

        // Act
        RecoveryJwtTokenDTO result = authenticateUserUseCase.execute(loginUserDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedToken.token(), result.token());
    }

    @Test
    void execute_ShouldCallUserServiceWithCorrectParameters() {
        // Arrange
        when(userService.authenticateUser(loginUserDto)).thenReturn(expectedToken);

        // Act
        authenticateUserUseCase.execute(loginUserDto);

        // Assert
        verify(userService, Mockito.times(1)).authenticateUser(loginUserDto);
    }
}
