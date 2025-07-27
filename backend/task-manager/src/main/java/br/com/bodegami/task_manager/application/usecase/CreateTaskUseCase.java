package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponse;
import org.springframework.http.HttpHeaders;

public interface CreateTaskUseCase {
    CreateTaskResponse execute(CreateTaskRequest request, HttpHeaders httpHeaders);
}
