package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskByIdUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private GetTaskByIdUseCaseImpl getTaskByIdUseCase;

    private final String userId = "550e8400-e29b-41d4-a716-446655440000";
    private final UUID taskId = UUID.fromString(userId);
    private final TaskDetailsResponse taskDetails = getTaskDetailsResponse();



    @Test
    void shouldGetTaskByIdSuccessfully() {
        when(taskService.findByTaskId(taskId)).thenReturn(taskDetails);

        TaskDetailsResponse result = getTaskByIdUseCase.execute(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.id());
        assertEquals("Test Task", result.title());
        verify(taskService, times(1)).findByTaskId(taskId);
    }

    @Test
    void shouldHandleNullTaskId() {
        doThrow(NullPointerException.class).when(taskService).findByTaskId(null);

        assertThrows(NullPointerException.class,
            () -> getTaskByIdUseCase.execute(null));

        verify(taskService, times(1)).findByTaskId(null);
    }

    @Test
    void shouldHandleNonExistentTask() {
        UUID nonExistentId = UUID.randomUUID();
        when(taskService.findByTaskId(nonExistentId)).thenReturn(null);

        TaskDetailsResponse result = getTaskByIdUseCase.execute(nonExistentId);

        assertNull(result);
        verify(taskService, times(1)).findByTaskId(nonExistentId);
    }

    private TaskDetailsResponse getTaskDetailsResponse() {
        return new TaskDetailsResponse(
                taskId,
                "Test Task",
                "Test Description",
                "PENDING",
                UUID.fromString(userId),
                LocalDateTime.of(1990, 2, 1, 0, 0),
                LocalDateTime.of(1990, 1, 1, 0, 0)
        );
    }
}
