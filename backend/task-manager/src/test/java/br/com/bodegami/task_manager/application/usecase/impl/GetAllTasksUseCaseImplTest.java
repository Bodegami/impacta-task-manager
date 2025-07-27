package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponse;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllTasksUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private GetAllTasksUseCaseImpl getAllTasksUseCase;

    private final UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private final List<TaskResponse> expectedTasks = getExpectedTasks();
    private final HttpHeaders httpHeaders = mock(HttpHeaders.class);

    @Test
    void shouldGetAllTasksForUserSuccessfully() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId.toString());
        when(taskService.findAllByUserId(userId)).thenReturn(expectedTasks);

        List<TaskResponse> result = getAllTasksUseCase.execute(httpHeaders);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
        verify(taskService, times(1)).findAllByUserId(userId);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksFound() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId.toString());
        when(taskService.findAllByUserId(userId)).thenReturn(List.of());

        List<TaskResponse> result = getAllTasksUseCase.execute(httpHeaders);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
        verify(taskService, times(1)).findAllByUserId(userId);
    }

    @Test
    void shouldHandleNullUserId() {
        when(userService.getUserIdFromToken(null))
                .thenThrow(new RuntimeException("Token with invalid user_id"));

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> getAllTasksUseCase.execute(null));

        assertEquals("Token with invalid user_id", result.getMessage());
        verify(userService, times(1)).getUserIdFromToken(null);
        verify(taskService, never()).findAllByUserId(null);
    }

    private List<TaskResponse> getExpectedTasks() {
        return List.of(
                new TaskResponse(
                        UUID.randomUUID(),
                        "Task 1",
                        "PENDING"
                ),
                new TaskResponse(
                        UUID.randomUUID(),
                        "Task 2",
                        "COMPLETED"
                )
        );
    }
}
