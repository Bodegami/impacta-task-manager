package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskByIdUseCaseImplTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private GetTaskByIdUseCaseImpl getTaskByIdUseCase;

    private UUID taskId;
    private TaskDetailsResponse taskDetails;

    @BeforeEach
    void setUp() {
        taskId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        taskDetails = new TaskDetailsResponse(
            taskId.toString(),
            "Test Task",
            "Test Description",
            "PENDING",
            null, null, null, null, null
        );
    }

    @Test
    void shouldGetTaskByIdSuccessfully() {
        when(taskService.findByTaskId(taskId)).thenReturn(taskDetails);

        TaskDetailsResponse result = getTaskByIdUseCase.execute(taskId);

        assertNotNull(result);
        assertEquals(taskId.toString(), result.id());
        assertEquals("Test Task", result.title());
        verify(taskService, times(1)).findByTaskId(taskId);
    }

    @Test
    void shouldHandleNullTaskId() {
        assertThrows(NullPointerException.class, 
            () -> getTaskByIdUseCase.execute(null));
    }

    @Test
    void shouldHandleNonExistentTask() {
        UUID nonExistentId = UUID.randomUUID();
        when(taskService.findByTaskId(nonExistentId)).thenReturn(null);

        TaskDetailsResponse result = getTaskByIdUseCase.execute(nonExistentId);
        
        assertNull(result);
        verify(taskService, times(1)).findByTaskId(nonExistentId);
    }
}
