package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    CreateTaskResponseDTO create(CreateTaskRequestDTO request, String userId);

    List<TaskResponseDTO> findAllByUserId(UUID userId);

    TaskDetailsResponse findByTaskId(UUID userId);

    void deleteTaskById(UUID taskId);

    TaskDetailsResponse updateTask(UUID taskId, UpdateTaskRequestDTO request);
}
