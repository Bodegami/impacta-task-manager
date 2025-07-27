package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchTasksUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private SearchTasksUseCaseImpl searchTasksUseCase;

    private final String userId = "550e8400-e29b-41d4-a716-446655440000";
    private final Map<String, String> searchParams = Map.of("status", "PENDING");
    private final List<TaskResponseDTO> expectedTasks = getExpectedTasks();
    private final HttpHeaders httpHeaders = mock(HttpHeaders.class);

    @Test
    void shouldSearchTasksSuccessfully() {
        when(userService.getUserIdFromToken(any(HttpHeaders.class))).thenReturn(userId);
        when(taskService.findAllByParams(userId, searchParams)).thenReturn(expectedTasks);

        List<TaskResponseDTO> result = searchTasksUseCase.execute(httpHeaders, searchParams);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskService).findAllByParams(userId, searchParams);
    }

    @Test
    void shouldHandleNullUserId() {
        when(userService.getUserIdFromToken(null))
                .thenThrow(new RuntimeException("Token with invalid user_id"));

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> searchTasksUseCase.execute(null, searchParams));

        assertEquals("Token with invalid user_id", result.getMessage());
        verify(userService, times(1)).getUserIdFromToken(null);
        verify(taskService, never()).findAllByParams(null, searchParams);
    }

    @Test
    void shouldHandleNullSearchParams() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId);
        when(taskService.findAllByParams(userId, null)).thenReturn(List.of());

        List<TaskResponseDTO> result = searchTasksUseCase.execute(httpHeaders, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
        verify(taskService, times(1)).findAllByParams(userId, null);
    }

    private List<TaskResponseDTO> getExpectedTasks() {
        return List.of(
                new TaskResponseDTO(
                        UUID.randomUUID(),
                        "Task 1",
                        "PENDING"
                ),
                new TaskResponseDTO(
                        UUID.randomUUID(),
                        "Task 2",
                        "PENDING"
                )
        );
    }
}
