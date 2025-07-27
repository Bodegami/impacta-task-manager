package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllTasksUseCaseImplTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private GetAllTasksUseCaseImpl getAllTasksUseCase;

    private UUID userId;
    private List<TaskResponseDTO> expectedTasks;

    @BeforeEach
    void setUp() {
        userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        expectedTasks = List.of(
            new TaskResponseDTO(
                UUID.randomUUID().toString(),
                "Task 1",
                "Description 1",
                "PENDING",
                null, null, null, null
            ),
            new TaskResponseDTO(
                UUID.randomUUID().toString(),
                "Task 2",
                "Description 2",
                "COMPLETED",
                null, null, null, null
            )
        );
    }

    @Test
    void shouldGetAllTasksForUserSuccessfully() {
        when(taskService.findAllByUserId(userId)).thenReturn(expectedTasks);

        List<TaskResponseDTO> result = getAllTasksUseCase.execute(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskService, times(1)).findAllByUserId(userId);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksFound() {
        when(taskService.findAllByUserId(userId)).thenReturn(List.of());

        List<TaskResponseDTO> result = getAllTasksUseCase.execute(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(taskService, times(1)).findAllByUserId(userId);
    }

    @Test
    void shouldHandleNullUserId() {
        assertThrows(NullPointerException.class, 
            () -> getAllTasksUseCase.execute(null));
        
        verify(taskService, never()).findAllByUserId(any());
    }
}
