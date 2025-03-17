package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;

public interface TaskService {
    CreateTaskResponseDTO create(CreateTaskRequestDTO request);
}
