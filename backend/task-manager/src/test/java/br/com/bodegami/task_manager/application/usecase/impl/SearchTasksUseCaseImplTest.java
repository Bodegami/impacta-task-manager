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
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchTasksUseCaseImplTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private SearchTasksUseCaseImpl searchTasksUseCase;

    private String userId;
    private Map<String, String> searchParams;
    private List<TaskResponseDTO> expectedTasks;

    @BeforeEach
    void setUp() {
        userId = "550e8400-e29b-41d4-a716-446655440000";
        searchParams = Map.of("status", "PENDING");
        expectedTasks = List.of(
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

    @Test
    void shouldSearchTasksSuccessfully() {
        when(taskService.findAllByParams(anyString(), any(Map.class)))
            .thenReturn(expectedTasks);

        List<TaskResponseDTO> result = searchTasksUseCase.execute(userId, searchParams);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskService).findAllByParams(userId, searchParams);
    }

    @Test
    void shouldHandleNullUserId() {
        doThrow(NullPointerException.class).when(taskService).findAllByParams(null, searchParams);

        assertThrows(NullPointerException.class,
            () -> searchTasksUseCase.execute(null, searchParams));

        verify(taskService, times(1)).findAllByParams(null, searchParams);
    }

    @Test
    void shouldHandleNullSearchParams() {
        when(taskService.findAllByParams(userId, null)).thenReturn(List.of());

        List<TaskResponseDTO> result = searchTasksUseCase.execute(userId, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
