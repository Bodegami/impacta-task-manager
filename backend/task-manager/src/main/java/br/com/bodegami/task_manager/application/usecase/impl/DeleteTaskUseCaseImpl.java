package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.usecase.DeleteTaskUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase {

    private final TaskService taskService;

    public DeleteTaskUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void execute(UUID taskId) {
        taskService.deleteTaskById(taskId);
    }
}
