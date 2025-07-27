package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import br.com.bodegami.task_manager.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetUserByIdUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private GetUserByIdUseCaseImpl getUserByIdUseCase;

    private UUID userId;
    private UserDetailsResponseDTO userDetails;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        userDetails = new UserDetailsResponseDTO(
                userId,
                "John Doe",
                "john.doe@example.com",
                "1990-01-01"
        );
    }

    @Test
    void shouldGetUserByIdSuccessfully() {
        // Arrange
        when(userService.findById(userId)).thenReturn(userDetails);

        // Act
        UserDetailsResponseDTO result = getUserByIdUseCase.execute(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals(userDetails.name(), result.name());
        assertEquals(userDetails.email(), result.email());
        
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userService.findById(any(UUID.class))).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> getUserByIdUseCase.execute(userId));
        verify(userService, times(1)).findById(userId);
    }
}
