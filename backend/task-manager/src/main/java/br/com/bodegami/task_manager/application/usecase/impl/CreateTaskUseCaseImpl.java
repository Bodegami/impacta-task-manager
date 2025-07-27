package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.application.usecase.CreateTaskUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.springframework.stereotype.Component;

@Component
public class CreateTaskUseCaseImpl implements CreateTaskUseCase {

    private final TaskService taskService;

    public CreateTaskUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public CreateTaskResponseDTO execute(CreateTaskRequestDTO request, String userId) {
        return taskService.create(request, userId);
    }
}
