package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TaskService {
    CreateTaskResponse create(CreateTaskRequest request, String userId);

    List<TaskResponse> findAllByUserId(UUID userId);

    TaskDetailsResponse findByTaskId(UUID userId);

    void deleteTaskById(UUID taskId);

    TaskDetailsResponse updateTask(UUID taskId, UpdateTaskRequest request);

    List<TaskResponse> findAllByParams(String userId, Map<String, String> params);
}
