package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponseDTO;

public interface CreateUserUseCase {
    CreateUserResponseDTO execute(CreateUserRequestDTO request);
}
