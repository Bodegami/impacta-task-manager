package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseImplTest {

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CreateTaskUseCaseImpl createTaskUseCase;

    private CreateTaskRequestDTO request;
    private CreateTaskResponseDTO response;
    private final String userId = "550e8400-e29b-41d4-a716-446655440000";
    private final HttpHeaders httpHeaders = mock(HttpHeaders.class);

    @BeforeEach
    void setUp() {
        request = new CreateTaskRequestDTO("Test Task", "Test Description", "PENDING");
        response = new CreateTaskResponseDTO(
                UUID.fromString(userId),
                "Test Task",
                "Test Description",
                "PENDING",
                null,
                LocalDateTime.of(1990, 2, 1, 0, 0) ,
                LocalDateTime.of(1990, 1, 1, 0, 0)
        );
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        when(taskService.create(request, userId)).thenReturn(response);
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId);

        CreateTaskResponseDTO result = createTaskUseCase.execute(request, httpHeaders);

        assertNotNull(result);
        assertEquals(response.id(), result.id());
        assertEquals(response.title(), result.title());
        verify(taskService, times(1)).create(request, userId);
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
    }

    @Test
    void shouldHandleNullRequest() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId);
        doThrow(NullPointerException.class).when(taskService).create(null, userId);

        assertThrows(NullPointerException.class,
            () -> createTaskUseCase.execute(null, httpHeaders));

        verify(taskService, times(1)).create(null, userId);
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
    }

    @Test
    void shouldHandleNullUserId() {
        when(userService.getUserIdFromToken(null))
                .thenThrow(new RuntimeException("Token with invalid user_id"));

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> createTaskUseCase.execute(request, null));

        assertEquals("Token with invalid user_id", result.getMessage());
        verify(userService, times(1)).getUserIdFromToken(null);
        verify(taskService, never()).create(request, null);
    }
}
