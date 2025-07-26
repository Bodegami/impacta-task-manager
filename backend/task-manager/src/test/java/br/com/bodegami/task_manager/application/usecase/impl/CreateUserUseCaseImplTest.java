package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import br.com.bodegami.task_manager.domain.entity.User;
import br.com.bodegami.task_manager.domain.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

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
                UserRole.USER
        );

        responseDTO = new CreateUserResponseDTO(
                UUID.randomUUID(),
                "John Doe",
                "john.doe@example.com",
                UserRole.USER
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
        assertEquals(responseDTO.role(), result.role());
        
        verify(userService, times(1)).create(requestDTO);
    }

    @Test
    void shouldHandleNullRequest() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> createUserUseCase.execute(null));
        verify(userService, never()).create(any());
    }
}
