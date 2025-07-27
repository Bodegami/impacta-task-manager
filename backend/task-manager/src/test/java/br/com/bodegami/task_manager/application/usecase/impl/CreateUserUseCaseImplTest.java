package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.BeforeEach;
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

    private CreateUserRequestDTO requestDTO;
    private CreateUserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new CreateUserRequestDTO(
                "John Doe",
                "john.doe@example.com",
                "password123",
                ROLE_CUSTOMER.name()
        );

        responseDTO = new CreateUserResponseDTO(
                UUID.randomUUID(),
                "John Doe",
                "john.doe@example.com",
                ROLE_CUSTOMER.name()
        );
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Arrange
        when(userService.create(any(CreateUserRequestDTO.class))).thenReturn(responseDTO);

        // Act
        CreateUserResponseDTO result = createUserUseCase.execute(requestDTO);

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
}
