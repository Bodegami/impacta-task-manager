package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.dto.UserResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import br.com.bodegami.task_manager.domain.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListUsersUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private ListUsersUseCaseImpl listUsersUseCase;

    private List<UserResponseDTO> userList;

    @BeforeEach
    void setUp() {
        userList = Arrays.asList(
                new UserResponseDTO(
                        UUID.randomUUID(),
                        "John Doe",
                        "john.doe@example.com",
                        UserRole.USER
                ),
                new UserResponseDTO(
                        UUID.randomUUID(),
                        "Jane Smith",
                        "jane.smith@example.com",
                        UserRole.ADMIN
                )
        );
    }

    @Test
    void shouldReturnListOfUsers() {
        // Arrange
        when(userService.findAll()).thenReturn(userList);

        // Act
        List<UserResponseDTO> result = listUsersUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userList.get(0).email(), result.get(0).email());
        assertEquals(userList.get(1).email(), result.get(1).email());
        
        verify(userService, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersFound() {
        // Arrange
        when(userService.findAll()).thenReturn(List.of());

        // Act
        List<UserResponseDTO> result = listUsersUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService, times(1)).findAll();
    }
}
