package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponseDTO;

import java.util.List;

public interface ListUsersUseCase {
    List<UserResponseDTO> execute();
}
