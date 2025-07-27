package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponse;

import java.util.List;
import java.util.UUID;

public interface GetTaskCommentsUseCase {
    List<TaskCommentResponse> execute(UUID taskId);
}
