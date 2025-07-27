package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

public interface GetAllTasksUseCase {
    List<TaskResponseDTO> execute(HttpHeaders httpHeaders);
}
