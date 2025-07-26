package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import br.com.bodegami.task_manager.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteUserUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        // Arrange
        doNothing().when(userService).delete(userId);

        // Act
        deleteUserUseCase.execute(userId);

        // Assert
        verify(userService, times(1)).delete(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        doThrow(new UserNotFoundException("User not found"))
                .when(userService).delete(userId);

        // Act & Assert
        assertThrows(UserNotFoundException.class, 
            () -> deleteUserUseCase.execute(userId));
        
        verify(userService, times(1)).delete(userId);
    }

    @Test
    void shouldHandleNullId() {
        // Act & Assert
        assertThrows(NullPointerException.class, 
            () -> deleteUserUseCase.execute(null));
        
        verify(userService, never()).delete(any());
    }
}
