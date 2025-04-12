package br.com.bodegami.task_manager.domain.service.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.domain.entity.Task;
import br.com.bodegami.task_manager.domain.entity.TaskSearchParam;
import br.com.bodegami.task_manager.domain.mapper.TaskMapper;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.infrastructure.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

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

        return searchByParam(userUuid, params);
    }

    @Transactional
    public void deleteTaskById(UUID taskId) {
        repository.deleteById(taskId);
    }

    private List<TaskResponseDTO> searchByParam(UUID userUuid, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return findAllByUserId(userUuid);
        }

        String paramKey = params.keySet().iterator().next();
        String paramValue = params.get(paramKey).toUpperCase(Locale.ROOT);

        TaskSearchParam searchParam = TaskSearchParam.fromKey(paramKey);

        Map<TaskSearchParam, Function<String, List<TaskResponseDTO>>> filterMap = Map.of(
                TaskSearchParam.TASK_ID, value -> repository.findById(UUID.fromString(value))
                        .map(task -> List.of(mapper.toFindAllResponse(task)))
                        .orElseGet(List::of),
                TaskSearchParam.TITLE, value -> toListResponseDto(repository.findAllByUserIdAndTitleIgnoreCase(userUuid, value)),
                TaskSearchParam.STATUS, value -> toListResponseDto(repository.findAllByUserIdAndStatusIgnoreCase(userUuid, value)),
                TaskSearchParam.DESCRIPTION, value -> toListResponseDto(repository.findAllByUserIdAndDescriptionContainingIgnoreCase(userUuid, value))
        );

        return filterMap.getOrDefault(searchParam, key -> List.of())
                .apply(paramValue);
    }

    private List<TaskResponseDTO> toListResponseDto(Optional<List<Task>> tasks) {
        return tasks.map(taskList -> taskList.stream()
                .map(mapper::toFindAllResponse)
                .toList())
        .orElse(List.of());
    }
}
