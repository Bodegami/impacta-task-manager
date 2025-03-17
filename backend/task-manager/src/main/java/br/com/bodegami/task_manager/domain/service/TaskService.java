package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    CreateTaskResponseDTO create(CreateTaskRequestDTO request);

    List<TaskResponseDTO> findAllByUserId(UUID userId);

    TaskDetailsResponse findByTaskId(UUID userId);
}
