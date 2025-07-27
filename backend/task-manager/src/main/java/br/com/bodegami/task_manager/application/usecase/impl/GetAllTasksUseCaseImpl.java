package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import br.com.bodegami.task_manager.application.usecase.GetAllTasksUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetAllTasksUseCaseImpl implements GetAllTasksUseCase {

    private final TaskService taskService;

    public GetAllTasksUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<TaskResponseDTO> execute(UUID userId) {
        return taskService.findAllByUserId(userId);
    }
}
