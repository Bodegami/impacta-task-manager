package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponse;
import br.com.bodegami.task_manager.application.usecase.CreateTaskUseCase;
import br.com.bodegami.task_manager.domain.entity.Task;
import br.com.bodegami.task_manager.domain.exception.UseCaseException;
import br.com.bodegami.task_manager.domain.mapper.TaskMapper;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateTaskUseCaseImpl implements CreateTaskUseCase {

    private final UserService userService;
    private final TaskService taskService;
    private final TaskMapper mapper;

    public CreateTaskUseCaseImpl(UserService userService, TaskService taskService, TaskMapper mapper) {
        this.userService = userService;
        this.taskService = taskService;
        this.mapper = mapper;
    }

    @Override
    public CreateTaskResponse execute(CreateTaskRequest request, HttpHeaders httpHeaders) {
        try {
            log.debug("Iniciando criação de tarefa para o usuário");

            String userId = userService.getUserIdFromToken(httpHeaders);
            Task entity = mapper.toDomain(request, userId);
            Task savedEntity = taskService.create(entity, userId);

            log.info("Tarefa criada com sucesso: {}", savedEntity.getId());
            return mapper.toCreateResponse(savedEntity);
        } catch (Exception e) {
            log.error("Erro ao criar tarefa: {}", e.getMessage(), e);
            throw new UseCaseException("Fail to create task", e);
        }
    }
}
