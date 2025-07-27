package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.entrypoint.dto.UpdateTaskRequestDTO;
import br.com.bodegami.task_manager.application.usecase.UpdateTaskUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateTaskUseCaseImpl implements UpdateTaskUseCase {

    private final TaskService taskService;

    public UpdateTaskUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public TaskDetailsResponse execute(UUID taskId, UpdateTaskRequestDTO request) {
        return taskService.updateTask(taskId, request);
    }
}
