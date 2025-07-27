package br.com.bodegami.task_manager.application.usecase.impl;

import br.com.bodegami.task_manager.application.usecase.AuthenticateUserUseCase;
import br.com.bodegami.task_manager.domain.service.UserService;
import br.com.bodegami.task_manager.security.LoginUserDTO;
import br.com.bodegami.task_manager.security.RecoveryJwtTokenDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final UserService userService;

    public AuthenticateUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public RecoveryJwtTokenDTO execute(LoginUserDTO loginUserDto) {
        return userService.authenticateUser(loginUserDto);
    }
}
