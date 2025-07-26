package br.com.bodegami.task_manager.application.usecase;

import java.util.UUID;

public interface DeleteUserUseCase {
    void execute(UUID id);
}
