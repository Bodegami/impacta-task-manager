package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponseDTO;
import br.com.bodegami.task_manager.application.usecase.GetTaskCommentsUseCase;
import br.com.bodegami.task_manager.domain.service.TaskCommentService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetTaskCommentsUseCaseImpl implements GetTaskCommentsUseCase {

    private final TaskCommentService taskCommentService;

    public GetTaskCommentsUseCaseImpl(TaskCommentService taskCommentService) {
        this.taskCommentService = taskCommentService;
    }

    @Override
    public List<TaskCommentResponseDTO> execute(UUID taskId) {
        return taskCommentService.getCommentsByTask(taskId);
    }
}
