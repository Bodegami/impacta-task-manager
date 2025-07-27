package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import br.com.bodegami.task_manager.application.usecase.SearchTasksUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SearchTasksUseCaseImpl implements SearchTasksUseCase {

    private final TaskService taskService;

    public SearchTasksUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<TaskResponseDTO> execute(String userId, Map<String, String> params) {
        return taskService.findAllByParams(userId, params);
    }
}
