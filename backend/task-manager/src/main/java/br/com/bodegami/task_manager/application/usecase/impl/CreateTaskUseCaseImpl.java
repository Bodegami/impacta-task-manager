package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponse;
import br.com.bodegami.task_manager.application.usecase.CreateTaskUseCase;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class CreateTaskUseCaseImpl implements CreateTaskUseCase {

    private final UserService userService;
    private final TaskService taskService;

    public CreateTaskUseCaseImpl(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public CreateTaskResponse execute(CreateTaskRequest request, HttpHeaders httpHeaders) {
        String userId = userService.getUserIdFromToken(httpHeaders);
        return taskService.create(request, userId);
    }
}
