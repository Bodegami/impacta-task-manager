package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponse;
import br.com.bodegami.task_manager.application.usecase.GetAllTasksUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetAllTasksUseCaseImpl implements GetAllTasksUseCase {

    private final UserService userService;
    private final TaskService taskService;

    public GetAllTasksUseCaseImpl(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public List<TaskResponse> execute(HttpHeaders httpHeaders) {
        String user = userService.getUserIdFromToken(httpHeaders);
        return taskService.findAllByUserId(UUID.fromString(user));
    }
}
