package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TaskCommentService {

    List<TaskCommentResponseDTO> getCommentsByTask(UUID taskId);

    TaskCommentResponseDTO addComment(UUID userId, TaskCommentRequestDTO dto);

}
