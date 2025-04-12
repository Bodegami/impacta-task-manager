package br.com.bodegami.task_manager.domain.service.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.domain.entity.Task;
import br.com.bodegami.task_manager.domain.mapper.TaskMapper;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.infrastructure.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper mapper;
    private final TaskRepository repository;

    public TaskServiceImpl(TaskMapper mapper, TaskRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    public CreateTaskResponseDTO create(CreateTaskRequestDTO request, String userId) {

        Task entity = mapper.toDomain(request, userId);

        Task result = repository.save(entity);

        CreateTaskResponseDTO response = mapper.toCreateResponse(result);

        return response;
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAllByUserId(UUID userId) {

        List<TaskResponseDTO> result = repository.findAllByUserId(userId)
                .stream()
                .map(mapper::toFindAllResponse)
                .toList();

        return result;
    }

    @Transactional(readOnly = true)
    public TaskDetailsResponse findByTaskId(UUID taskId) {

        TaskDetailsResponse result = repository.findById(taskId)
                .map(mapper::toTaskDetailsResponse)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        return result;
    }

    @Transactional
    public TaskDetailsResponse updateTask(UUID taskId, UpdateTaskRequestDTO request) {

        Task task = repository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        mapper.toUpdateDomain(task, request);

        TaskDetailsResponse response = mapper.toTaskDetailsResponse(task);

        return response;
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAllByParams(String userId, Map<String, String> params) {

        UUID userUuid = UUID.fromString(userId);
        String paramKey = params.keySet().iterator().next();
        String paramValue = params.get(paramKey);

        return searchByParam(userId, paramKey, paramValue, userUuid);
    }

    @Transactional
    public void deleteTaskById(UUID taskId) {
        repository.deleteById(taskId);
    }

    private List<TaskResponseDTO> searchByParam(String userId, String paramKey, String paramValue, UUID userUuid) {
        switch (paramKey) {
            case "taskId" -> {
                return repository.findById(UUID.fromString(paramValue))
                        .map(task -> List.of(mapper.toFindAllResponse(task)))
                        .orElseGet(List::of);
            }
            case "titulo" -> {
                Optional<List<Task>> optionalTasks = repository.findAllByUserIdAndTitle(userUuid, paramValue);
                return toListResponseDto(optionalTasks);
            }
            case "status" -> {
                Optional<List<Task>> optionalTasks = repository.findAllByUserIdAndStatus(userUuid, paramValue);
                return toListResponseDto(optionalTasks);
            }
            case "descricao" -> {
                Optional<List<Task>> optionalTasks = repository.findAllByUserIdAndDescriptionContaining(userUuid, paramValue);
                return toListResponseDto(optionalTasks);
            }
            default -> {
                return findAllByUserId(UUID.fromString(userId));
            }
        }
    }

    private List<TaskResponseDTO> toListResponseDto(Optional<List<Task>> tasks) {
        return tasks.map(taskList -> taskList.stream()
                .map(mapper::toFindAllResponse)
                .toList())
        .orElse(List.of());
    }
}
