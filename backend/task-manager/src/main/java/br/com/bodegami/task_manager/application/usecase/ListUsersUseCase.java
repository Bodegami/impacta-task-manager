package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.dto.UserResponseDTO;

import java.util.List;

public interface ListUsersUseCase {
    List<UserResponseDTO> execute();
}
