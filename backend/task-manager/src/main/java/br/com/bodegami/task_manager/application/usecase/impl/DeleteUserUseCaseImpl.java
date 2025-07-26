package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.usecase.DeleteUserUseCase;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserService userService;

    public DeleteUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(UUID id) {
        userService.delete(id);
    }
}
