package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import org.springframework.http.HttpHeaders;

public interface CreateTaskUseCase {
    CreateTaskResponseDTO execute(CreateTaskRequestDTO request, HttpHeaders httpHeaders);
}
