package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseImplTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private CreateTaskUseCaseImpl createTaskUseCase;

    private CreateTaskRequestDTO request;
    private CreateTaskResponseDTO response;
    private final String userId = "550e8400-e29b-41d4-a716-446655440000";

    @BeforeEach
    void setUp() {
        request = new CreateTaskRequestDTO("Test Task", "Test Description", "PENDING");
        response = new CreateTaskResponseDTO("550e8400-e29b-41d4-a716-446655440001", "Test Task");
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        when(taskService.create(any(CreateTaskRequestDTO.class), anyString()))
                .thenReturn(response);

        CreateTaskResponseDTO result = createTaskUseCase.execute(request, userId);

        assertNotNull(result);
        assertEquals(response.id(), result.id());
        assertEquals(response.title(), result.title());
    }

    @Test
    void shouldHandleNullRequest() {
        assertThrows(NullPointerException.class, 
            () -> createTaskUseCase.execute(null, userId));
    }

    @Test
    void shouldHandleNullUserId() {
        assertThrows(NullPointerException.class, 
            () -> createTaskUseCase.execute(request, null));
    }
}
