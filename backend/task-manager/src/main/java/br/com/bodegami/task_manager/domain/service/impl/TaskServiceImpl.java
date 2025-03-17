package br.com.bodegami.task_manager.domain.service.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.domain.entity.Task;
import br.com.bodegami.task_manager.domain.mapper.TaskMapper;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.infrastructure.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper mapper;
    private final TaskRepository repository;

    public TaskServiceImpl(TaskMapper mapper, TaskRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public CreateTaskResponseDTO create(CreateTaskRequestDTO request) {

        Task entity = mapper.toDomain(request);

        Task result = repository.save(entity);

        CreateTaskResponseDTO response = mapper.toCreateResponse(result);

        return response;
    }
}
