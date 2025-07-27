package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponse;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface GetAllTasksUseCase {
    List<TaskResponse> execute(HttpHeaders httpHeaders);
}
