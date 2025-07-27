package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddTaskCommentUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private AddTaskCommentUseCaseImpl addTaskCommentUseCase;

    private final UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private final TaskCommentRequestDTO request = new TaskCommentRequestDTO("Test comment", UUID.randomUUID().toString());
    private final TaskCommentResponseDTO expectedResponse = getTaskCommentResponseDTO();
    private final HttpHeaders httpHeaders = mock(HttpHeaders.class);

    @Test
    void shouldAddCommentSuccessfully() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId.toString());
        when(taskCommentService.addComment(userId, request)).thenReturn(expectedResponse);

        TaskCommentResponseDTO result = addTaskCommentUseCase.execute(httpHeaders, request);

        assertNotNull(result);
        assertEquals(expectedResponse.id(), result.id());
        assertEquals(expectedResponse.comment(), result.comment());
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
        verify(taskCommentService, times(1)).addComment(userId, request);
    }

    @Test
    void shouldHandleNullUserId() {
        when(userService.getUserIdFromToken(null))
                .thenThrow(new RuntimeException("Token with invalid user_id"));

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> addTaskCommentUseCase.execute(null, request));

        assertEquals("Token with invalid user_id", result.getMessage());
        verify(userService, times(1)).getUserIdFromToken(null);
        verify(taskCommentService, never()).addComment(null, request);
    }

    @Test
    void shouldHandleNullRequest() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId.toString());
        when(taskCommentService.addComment(userId, null))
                .thenThrow(new RuntimeException("Payload invalido"));

        RuntimeException result = assertThrows(RuntimeException.class,
                () -> addTaskCommentUseCase.execute(httpHeaders, null));

        assertEquals("Payload invalido", result.getMessage());
        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
        verify(taskCommentService, times(1)).addComment(userId, null);
    }

    @Test
    void shouldHandleServiceException() {
        when(userService.getUserIdFromToken(httpHeaders)).thenReturn(userId.toString());
        when(taskCommentService.addComment(userId, request))
            .thenThrow(new RuntimeException("Tarefa não encontrada"));

        assertThrows(RuntimeException.class,
            () -> addTaskCommentUseCase.execute(httpHeaders, request));

        verify(userService, times(1)).getUserIdFromToken(httpHeaders);
        verify(taskCommentService, times(1)).addComment(userId, request);
    }

    private TaskCommentResponseDTO getTaskCommentResponseDTO() {
        return new TaskCommentResponseDTO(
                UUID.randomUUID(),
                "Test comment",
                "test@example.com",
                LocalDateTime.now()
        );
    }
}
