package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;
import br.com.bodegami.task_manager.application.usecase.BaseUseCaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskCommentsUseCaseImplTest extends BaseUseCaseTest {

    @InjectMocks
    private GetTaskCommentsUseCaseImpl getTaskCommentsUseCase;

    private final UUID taskId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
    private final List<TaskCommentResponseDTO> expectedComments = getTaskCommentResponseDTOList();

    @Test
    void shouldGetTaskCommentsSuccessfully() {
        when(taskCommentService.getCommentsByTask(taskId)).thenReturn(expectedComments);

        List<TaskCommentResponseDTO> result = getTaskCommentsUseCase.execute(taskId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskCommentService, times(1)).getCommentsByTask(taskId);
    }

    @Test
    void shouldReturnEmptyListWhenNoCommentsFound() {
        when(taskCommentService.getCommentsByTask(taskId)).thenReturn(List.of());

        List<TaskCommentResponseDTO> result = getTaskCommentsUseCase.execute(taskId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(taskCommentService, times(1)).getCommentsByTask(taskId);
    }

    @Test
    void shouldHandleNullTaskId() {
        doThrow(NullPointerException.class).when(taskCommentService).getCommentsByTask(null);

        assertThrows(NullPointerException.class,
            () -> getTaskCommentsUseCase.execute(null));

        verify(taskCommentService, times(1)).getCommentsByTask(null);
    }

    private List<TaskCommentResponseDTO> getTaskCommentResponseDTOList() {
        return List.of(
                new TaskCommentResponseDTO(
                        UUID.randomUUID(),
                        "First comment",
                        "user1@example.com",
                        LocalDateTime.now().minusDays(1)
                ),
                new TaskCommentResponseDTO(
                        UUID.randomUUID(),
                        "Second comment",
                        "user2@example.com",
                        LocalDateTime.now()
                )
        );
    }
}
