package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponse;
import br.com.bodegami.task_manager.application.usecase.CreateUserUseCase;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserService userService;

    public CreateUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CreateUserResponse execute(CreateUserRequest request) {
        return userService.create(request);
    }
}
