package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.UpdateUserRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponse;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import br.com.bodegami.task_manager.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    private final UUID userId = UUID.randomUUID();
    private final UpdateUserRequest updateRequest = getUserRequestDTO();;
    private final UserDetailsResponse updatedUser = getUserDetailsResponseDTO();

    @Test
    void shouldUpdateUserSuccessfully() {
        // Arrange
        when(userService.update(eq(userId), any(UpdateUserRequest.class)))
                .thenReturn(updatedUser);

        // Act
        UserDetailsResponse result = updateUserUseCase.execute(userId, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals(updateRequest.name(), result.name());
        assertEquals(updateRequest.email(), result.email());
        
        verify(userService, times(1)).update(eq(userId), any(UpdateUserRequest.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userService.update(any(), any())).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, 
            () -> updateUserUseCase.execute(userId, updateRequest));
        
        verify(userService, times(1)).update(eq(userId), any(UpdateUserRequest.class));
    }

    @Test
    void shouldHandleNullRequest() {
        // Act & Assert
        doThrow(NullPointerException.class).when(userService).update(userId, null);

        assertThrows(NullPointerException.class, 
            () -> updateUserUseCase.execute(userId, null));
        
        verify(userService, times(1)).update(userId, null);
    }

    private UserDetailsResponse getUserDetailsResponseDTO() {
        return new UserDetailsResponse(
                userId,
                "John Updated",
                "john.updated@example.com",
                "1990-01-01"
        );
    }

    private UpdateUserRequest getUserRequestDTO() {
        return new UpdateUserRequest(
                "John Updated",
                "john.updated@example.com"
        );
    }
}
