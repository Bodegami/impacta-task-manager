package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponse;

import java.util.List;

public interface ListUsersUseCase {
    List<UserResponse> execute();
}
