package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.dto.UpdateUserRequestDTO;
import br.com.bodegami.task_manager.application.dto.UserDetailsResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import br.com.bodegami.task_manager.domain.enums.UserRole;
import br.com.bodegami.task_manager.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    private UUID userId;
    private UpdateUserRequestDTO updateRequest;
    private UserDetailsResponseDTO updatedUser;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        
        updateRequest = new UpdateUserRequestDTO(
                "John Updated",
                "john.updated@example.com",
                "newPassword123",
                "12345678901",
                LocalDate.of(1990, 1, 1)
        );

        updatedUser = new UserDetailsResponseDTO(
                userId,
                "John Updated",
                "john.updated@example.com",
                UserRole.USER,
                "12345678901",
                "1990-01-01"
        );
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        when(userService.update(eq(userId), any(UpdateUserRequestDTO.class)))
                .thenReturn(updatedUser);

        // Act
        UserDetailsResponseDTO result = updateUserUseCase.execute(userId, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals(updateRequest.name(), result.name());
        assertEquals(updateRequest.email(), result.email());
        
        verify(userService, times(1)).update(eq(userId), any(UpdateUserRequestDTO.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userService.update(any(), any())).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, 
            () -> updateUserUseCase.execute(userId, updateRequest));
        
        verify(userService, times(1)).update(eq(userId), any(UpdateUserRequestDTO.class));
    }

    @Test
    void shouldHandleNullRequest() {
        // Act & Assert
        assertThrows(NullPointerException.class, 
            () -> updateUserUseCase.execute(userId, null));
        
        verify(userService, never()).update(any(), any());
    }
}
