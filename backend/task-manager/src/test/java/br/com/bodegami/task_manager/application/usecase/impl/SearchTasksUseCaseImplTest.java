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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
                "PENDING",
                null, null, null, null
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
        assertThrows(NullPointerException.class, 
            () -> searchTasksUseCase.execute(null, searchParams));
    }

    @Test
    void shouldHandleNullSearchParams() {
        when(taskService.findAllByParams(anyString(), any(Map.class)))
            .thenReturn(List.of());

        List<TaskResponseDTO> result = searchTasksUseCase.execute(userId, null);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
