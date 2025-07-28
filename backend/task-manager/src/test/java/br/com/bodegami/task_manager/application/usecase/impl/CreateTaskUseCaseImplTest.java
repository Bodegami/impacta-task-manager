package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponse;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import br.com.bodegami.task_manager.domain.entity.Task;
import br.com.bodegami.task_manager.domain.exception.UseCaseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseImplTest extends BaseUseCaseTest {

    public static final String FAIL_TO_CREATE_TASK = "Fail to create task";

    @InjectMocks
    private CreateTaskUseCaseImpl createTaskUseCase;

    private final CreateTaskRequest request =
            new CreateTaskRequest("Test Task", "Test Description", "PENDING");
    private final CreateTaskResponse response = getTaskResponseDTO();
    private final String userId = "550e8400-e29b-41d4-a716-446655440000";
    private final HttpHeaders httpHeaders = mock(HttpHeaders.class);
    private final Task task = createTask();

    @Test
    void shouldCreateTaskSuccessfully() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId);
        when(taskMapper.toDomain(request, userId)).thenReturn(task);
        when(taskService.create(any(Task.class), eq(userId))).thenReturn(task);
        when(taskMapper.toCreateResponse(task)).thenReturn(response);

        CreateTaskResponse result = createTaskUseCase.execute(request, httpHeaders);

        assertNotNull(result);
        assertEquals(response.id(), result.id());
        assertEquals(response.title(), result.title());
        verify(taskService, times(1)).create(any(Task.class), eq(userId));
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
        verify(taskMapper, times(1)).toDomain(request, userId);
        verify(taskMapper, times(1)).toCreateResponse(task);
    }

    @Test
    void shouldHandleNullRequest() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId);
        when(taskMapper.toDomain(null, userId))
                .thenThrow(new UseCaseException(FAIL_TO_CREATE_TASK));

        UseCaseException result = assertThrows(UseCaseException.class,
                () -> createTaskUseCase.execute(null, httpHeaders));

        assertEquals(FAIL_TO_CREATE_TASK, result.getMessage());
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
        verify(taskMapper, times(1)).toDomain(null, userId);
        verify(taskService, never()).create(any(), any());
    }

    @Test
    void shouldHandleNullUserId() {
        when(userService.getUserIdFromToken(null))
                .thenThrow(new RuntimeException("Token with invalid user_id"));

        UseCaseException result = assertThrows(UseCaseException.class,
                () -> createTaskUseCase.execute(request, null));

        assertEquals(FAIL_TO_CREATE_TASK, result.getMessage());
        verify(userService, times(1)).getUserIdFromToken(null);
        verify(taskService, never()).create(any(), any());
    }

    private CreateTaskResponse getTaskResponseDTO() {
        return new CreateTaskResponse(
                UUID.fromString(userId),
                "Test Task",
                "Test Description",
                "PENDING",
                null,
                LocalDateTime.of(1990, 2, 1, 0, 0),
                LocalDateTime.of(1990, 1, 1, 0, 0)
        );
    }

    private Task createTask() {
        return Task.builder()
                .id(UUID.fromString(userId))
                .title("Test Task")
                .description("Test Description")
                .status("PENDING")
                .createdAt(LocalDateTime.of(1990, 1, 1, 0, 0))
                .dueDate(LocalDateTime.of(1990, 2, 1, 0, 0))
                .build();
    }
}
