package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;
import br.com.bodegami.task_manager.application.usecase.AddTaskCommentUseCase;
import br.com.bodegami.task_manager.domain.service.TaskCommentService;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddTaskCommentUseCaseImpl implements AddTaskCommentUseCase {

    private final UserService userService;
    private final TaskCommentService taskCommentService;

    public AddTaskCommentUseCaseImpl(UserService userService, TaskCommentService taskCommentService) {
        this.userService = userService;
        this.taskCommentService = taskCommentService;
    }

    @Override
    public TaskCommentResponseDTO execute(HttpHeaders httpHeaders, TaskCommentRequestDTO request) {
        String userId = userService.getUserIdFromToken(httpHeaders);
        return taskCommentService.addComment(UUID.fromString(userId), request);
    }
}
