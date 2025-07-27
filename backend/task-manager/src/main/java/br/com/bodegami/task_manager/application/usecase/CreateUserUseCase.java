package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse execute(CreateUserRequest request);
}
