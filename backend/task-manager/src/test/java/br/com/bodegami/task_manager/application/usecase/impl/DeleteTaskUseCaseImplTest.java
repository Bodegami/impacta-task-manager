package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTaskUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private DeleteTaskUseCaseImpl deleteTaskUseCase;

    private final UUID taskId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

    @Test
    void shouldDeleteTaskSuccessfully() {
        doNothing().when(taskService).deleteTaskById(taskId);

        deleteTaskUseCase.execute(taskId);

        verify(taskService, times(1)).deleteTaskById(taskId);
    }

    @Test
    void shouldHandleNullTaskId() {
        doThrow(NullPointerException.class).when(taskService).deleteTaskById(null);

        assertThrows(NullPointerException.class, 
            () -> deleteTaskUseCase.execute(null));
        
        verify(taskService, times(1)).deleteTaskById(null);
    }

    @Test
    void shouldHandleNonExistentTask() {
        UUID nonExistentId = UUID.randomUUID();
        doNothing().when(taskService).deleteTaskById(nonExistentId);

        assertDoesNotThrow(() -> deleteTaskUseCase.execute(nonExistentId));
        verify(taskService, times(1)).deleteTaskById(nonExistentId);
    }
}
