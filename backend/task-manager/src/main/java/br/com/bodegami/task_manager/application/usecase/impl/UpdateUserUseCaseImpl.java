package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.UpdateUserRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponseDTO;
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
    public UserDetailsResponseDTO execute(UUID id, UpdateUserRequestDTO request) {
        return userService.update(id, request);
    }
}
