package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import java.util.UUID;

public interface GetTaskByIdUseCase {
    TaskDetailsResponse execute(UUID taskId);
}
