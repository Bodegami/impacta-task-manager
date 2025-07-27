package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import br.com.bodegami.task_manager.application.usecase.SearchTasksUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SearchTasksUseCaseImpl implements SearchTasksUseCase {

    private final UserService userService;
    private final TaskService taskService;

    public SearchTasksUseCaseImpl(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public List<TaskResponseDTO> execute(HttpHeaders httpHeaders, Map<String, String> params) {
        String userId = userService.getUserIdFromToken(httpHeaders);
        return taskService.findAllByParams(userId, params);
    }
}
