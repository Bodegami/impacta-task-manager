package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.usecase.GetTaskByIdUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetTaskByIdUseCaseImpl implements GetTaskByIdUseCase {

    private final TaskService taskService;

    public GetTaskByIdUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public TaskDetailsResponse execute(UUID taskId) {
        return taskService.findByTaskId(taskId);
    }
}
