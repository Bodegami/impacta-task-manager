package br.com.bodegami.task_manager.domain.service.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import br.com.bodegami.task_manager.domain.entity.Task;
import br.com.bodegami.task_manager.domain.mapper.TaskMapper;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.infrastructure.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<TaskResponseDTO> findAllByUserId(UUID userId) {

        List<TaskResponseDTO> result = repository.findAllByUserId(userId)
                .stream()
                .map(mapper::toFindAllResponse)
                .toList();

        return result;
    }

    public TaskDetailsResponse findByTaskId(UUID taskId) {

        TaskDetailsResponse result = repository.findById(taskId)
                .map(mapper::toTaskDetailsResponse)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        return result;
    }
}
