package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

public interface SearchTasksUseCase {
    List<TaskResponseDTO> execute(HttpHeaders httpHeaders, Map<String, String> params);
}
