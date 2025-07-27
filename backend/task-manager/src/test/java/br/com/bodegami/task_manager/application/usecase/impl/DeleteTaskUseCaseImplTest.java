package br.com.bodegami.task_manager.application.usecase.impl;

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
class DeleteTaskUseCaseImplTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private DeleteTaskUseCaseImpl deleteTaskUseCase;

    private UUID taskId;

    @BeforeEach
    void setUp() {
        taskId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    }

    @Test
    void shouldDeleteTaskSuccessfully() {
        doNothing().when(taskService).deleteTaskById(taskId);

        deleteTaskUseCase.execute(taskId);

        verify(taskService, times(1)).deleteTaskById(taskId);
    }

    @Test
    void shouldHandleNullTaskId() {
        assertThrows(NullPointerException.class, 
            () -> deleteTaskUseCase.execute(null));
        
        verify(taskService, never()).deleteTaskById(any());
    }

    @Test
    void shouldHandleNonExistentTask() {
        UUID nonExistentId = UUID.randomUUID();
        doNothing().when(taskService).deleteTaskById(nonExistentId);

        assertDoesNotThrow(() -> deleteTaskUseCase.execute(nonExistentId));
        verify(taskService, times(1)).deleteTaskById(nonExistentId);
    }
}
