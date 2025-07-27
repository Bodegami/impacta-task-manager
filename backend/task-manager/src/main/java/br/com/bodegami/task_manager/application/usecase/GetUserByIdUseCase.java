package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponse;

import java.util.UUID;

public interface GetUserByIdUseCase {
    UserDetailsResponse execute(UUID id);
}
