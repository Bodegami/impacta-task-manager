package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;
import org.springframework.http.HttpHeaders;

import java.util.UUID;

public interface AddTaskCommentUseCase {
    TaskCommentResponseDTO execute(HttpHeaders httpHeaders, TaskCommentRequestDTO request);
}
