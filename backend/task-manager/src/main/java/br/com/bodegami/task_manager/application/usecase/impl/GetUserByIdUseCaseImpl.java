package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponse;
import br.com.bodegami.task_manager.application.usecase.GetUserByIdUseCase;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {

    private final UserService userService;

    public GetUserByIdUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetailsResponse execute(UUID id) {
        return userService.findById(id);
    }
}
