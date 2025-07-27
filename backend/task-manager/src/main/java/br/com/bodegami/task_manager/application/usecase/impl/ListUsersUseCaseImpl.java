package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponseDTO;
import br.com.bodegami.task_manager.application.usecase.ListUsersUseCase;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListUsersUseCaseImpl implements ListUsersUseCase {

    private final UserService userService;

    public ListUsersUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserResponseDTO> execute() {
        return userService.findAll();
    }
}
