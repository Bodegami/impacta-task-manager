package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponse;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

import static br.com.bodegami.task_manager.security.RoleName.ROLE_CUSTOMER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateUserUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    private final CreateUserRequest requestDTO = getUserRequestDTO();
    private final CreateUserResponse responseDTO = getUserResponseDTO();

    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        when(userService.create(any(CreateUserRequest.class))).thenReturn(responseDTO);

        // Act
        CreateUserResponse result = createUserUseCase.execute(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(responseDTO.id(), result.id());
        assertEquals(responseDTO.name(), result.name());
        assertEquals(responseDTO.email(), result.email());
        assertEquals(responseDTO.createdAt(), result.createdAt());
        
        verify(userService, times(1)).create(requestDTO);
    }

    @Test
    void shouldHandleNullRequest() {
        // Arrange & Act & Assert
        doThrow(NullPointerException.class).when(userService).create(null);

        assertThrows(NullPointerException.class, () -> createUserUseCase.execute(null));
        verify(userService, times(1)).create(null);
    }

    private CreateUserRequest getUserRequestDTO() {
        return new CreateUserRequest(
                "John Doe",
                "john.doe@example.com",
                "password123",
                ROLE_CUSTOMER.name()
        );
    }

    private CreateUserResponse getUserResponseDTO() {
        return new CreateUserResponse(
                UUID.randomUUID(),
                "John Doe",
                "john.doe@example.com",
                ROLE_CUSTOMER.name()
        );
    }
}
