package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface GetTaskCommentsUseCase {
    List<TaskCommentResponseDTO> execute(UUID taskId);
}
