package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
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

    private final List<UserResponseDTO> userList = getUserResponseDTOList();

    @Test
    void shouldReturnListOfUsers() {
        // Arrange
        when(userService.findAll()).thenReturn(userList);

        // Act
        List<UserResponseDTO> result = listUsersUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userList.get(0).name(), result.get(0).name());
        assertEquals(userList.get(1).name(), result.get(1).name());
        
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

    private List<UserResponseDTO> getUserResponseDTOList() {
        return Arrays.asList(
                new UserResponseDTO(
                        UUID.randomUUID(),
                        "John Doe"
                ),
                new UserResponseDTO(
                        UUID.randomUUID(),
                        "Jane Smith"
                )
        );
    }
}
