package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponse;
import org.springframework.http.HttpHeaders;

public interface AddTaskCommentUseCase {
    TaskCommentResponse execute(HttpHeaders httpHeaders, TaskCommentRequest request);
}
