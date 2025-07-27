package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.entrypoint.dto.UpdateTaskRequestDTO;

import java.util.UUID;

public interface UpdateTaskUseCase {

    TaskDetailsResponse execute(UUID taskId, UpdateTaskRequestDTO request);
}
