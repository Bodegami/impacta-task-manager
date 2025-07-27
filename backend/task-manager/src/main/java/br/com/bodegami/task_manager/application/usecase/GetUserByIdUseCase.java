package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponseDTO;

import java.util.UUID;

public interface GetUserByIdUseCase {
    UserDetailsResponseDTO execute(UUID id);
}
