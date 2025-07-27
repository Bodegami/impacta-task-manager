package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.UpdateUserRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponse;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserDetailsResponse execute(UUID id, UpdateUserRequest request);
}
