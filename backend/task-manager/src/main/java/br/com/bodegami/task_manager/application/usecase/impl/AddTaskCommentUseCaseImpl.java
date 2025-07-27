package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;
import br.com.bodegami.task_manager.application.usecase.AddTaskCommentUseCase;
import br.com.bodegami.task_manager.domain.service.TaskCommentService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddTaskCommentUseCaseImpl implements AddTaskCommentUseCase {

    private final TaskCommentService taskCommentService;

    public AddTaskCommentUseCaseImpl(TaskCommentService taskCommentService) {
        this.taskCommentService = taskCommentService;
    }

    @Override
    public TaskCommentResponseDTO execute(UUID userId, TaskCommentRequestDTO request) {
        return taskCommentService.addComment(userId, request);
    }
}
