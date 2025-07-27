package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;

public interface CreateTaskUseCase {
    CreateTaskResponseDTO execute(CreateTaskRequestDTO request, String userId);
}
