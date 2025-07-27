package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;

import java.util.List;
import java.util.Map;

public interface SearchTasksUseCase {
    List<TaskResponseDTO> execute(String userId, Map<String, String> params);
}
