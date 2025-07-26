package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.dto.UpdateUserRequestDTO;
import br.com.bodegami.task_manager.application.dto.UserDetailsResponseDTO;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserDetailsResponseDTO execute(UUID id, UpdateUserRequestDTO request);
}
