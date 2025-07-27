package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;
import br.com.bodegami.task_manager.domain.service.TaskCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddTaskCommentUseCaseImplTest {

    @Mock
    private TaskCommentService taskCommentService;

    @InjectMocks
    private AddTaskCommentUseCaseImpl addTaskCommentUseCase;

    private UUID userId;
    private TaskCommentRequestDTO request;
    private TaskCommentResponseDTO expectedResponse;

    @BeforeEach
    void setUp() {
        userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        request = new TaskCommentRequestDTO("Test comment", UUID.randomUUID().toString());
        expectedResponse = new TaskCommentResponseDTO(
            UUID.randomUUID().toString(),
            "Test comment",
            "test@example.com",
            LocalDateTime.now()
        );
    }

    @Test
    void shouldAddCommentSuccessfully() {
        when(taskCommentService.addComment(any(UUID.class), any(TaskCommentRequestDTO.class)))
            .thenReturn(expectedResponse);

        TaskCommentResponseDTO result = addTaskCommentUseCase.execute(userId, request);

        assertNotNull(result);
        assertEquals(expectedResponse.id(), result.id());
        assertEquals(expectedResponse.comment(), result.comment());
        verify(taskCommentService, times(1)).addComment(userId, request);
    }

    @Test
    void shouldHandleNullUserId() {
        assertThrows(NullPointerException.class, 
            () -> addTaskCommentUseCase.execute(null, request));
        
        verify(taskCommentService, never()).addComment(any(), any());
    }

    @Test
    void shouldHandleNullRequest() {
        assertThrows(NullPointerException.class, 
            () -> addTaskCommentUseCase.execute(userId, null));
        
        verify(taskCommentService, never()).addComment(any(), any());
    }

    @Test
    void shouldHandleServiceException() {
        when(taskCommentService.addComment(any(UUID.class), any(TaskCommentRequestDTO.class)))
            .thenThrow(new RuntimeException("Service error"));

        assertThrows(RuntimeException.class, 
            () -> addTaskCommentUseCase.execute(userId, request));
        
        verify(taskCommentService, times(1)).addComment(userId, request);
    }
}
