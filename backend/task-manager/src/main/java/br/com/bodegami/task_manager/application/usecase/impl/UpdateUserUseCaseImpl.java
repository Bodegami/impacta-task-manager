package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.UpdateUserRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponse;
import br.com.bodegami.task_manager.application.usecase.UpdateUserUseCase;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserService userService;

    public UpdateUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetailsResponse execute(UUID id, UpdateUserRequest request) {
        return userService.update(id, request);
    }
}
