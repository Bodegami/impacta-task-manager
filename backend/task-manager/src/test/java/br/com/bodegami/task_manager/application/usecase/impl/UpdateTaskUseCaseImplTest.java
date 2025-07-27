package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.entrypoint.dto.UpdateTaskRequestDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTaskUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private UpdateTaskUseCaseImpl updateTaskUseCase;

    @Test
    @DisplayName("Should update task successfully")
    void testExecute_shouldUpdateTask() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        UpdateTaskRequestDTO request = new UpdateTaskRequestDTO("Updated Task", "Updated Description",
                "status", "1990-01-01");
        TaskDetailsResponse expectedResponse = new TaskDetailsResponse(
                taskId,
                request.title(),
                request.description(),
                "status",
                null,
                LocalDateTime.of(1990, 1, 1, 0, 0, 0),
                null
        );

        when(taskService.updateTask(taskId, request))
                .thenReturn(expectedResponse);

        // Act
        TaskDetailsResponse response = updateTaskUseCase.execute(taskId, request);

        // Assert
        assertNotNull(response);
        assertEquals(taskId, response.id());
        assertEquals(request.title(), response.title());
        assertEquals(request.description(), response.description());
        verify(taskService, times(1)).updateTask(taskId, request);
    }
}