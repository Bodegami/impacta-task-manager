package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponse;
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

    private final List<UserResponse> userList = getUserResponseDTOList();

    @Test
    void shouldReturnListOfUsers() {
        // Arrange
        when(userService.findAll()).thenReturn(userList);

        // Act
        List<UserResponse> result = listUsersUseCase.execute();

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
        List<UserResponse> result = listUsersUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService, times(1)).findAll();
    }

    private List<UserResponse> getUserResponseDTOList() {
        return Arrays.asList(
                new UserResponse(
                        UUID.randomUUID(),
                        "John Doe"
                ),
                new UserResponse(
                        UUID.randomUUID(),
                        "Jane Smith"
                )
        );
    }
}
