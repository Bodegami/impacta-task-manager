package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.dto.CreateUserResponseDTO;
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
    public CreateUserResponseDTO execute(CreateUserRequestDTO request) {
        return userService.create(request);
    }
}
