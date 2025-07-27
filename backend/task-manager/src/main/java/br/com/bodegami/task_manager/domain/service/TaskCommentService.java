package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponse;

import java.util.List;
import java.util.UUID;

public interface TaskCommentService {

    List<TaskCommentResponse> getCommentsByTask(UUID taskId);

    TaskCommentResponse addComment(UUID userId, TaskCommentRequest dto);

}
