package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.dto.CreateUserResponseDTO;

public interface CreateUserUseCase {
    CreateUserResponseDTO execute(CreateUserRequestDTO request);
}
