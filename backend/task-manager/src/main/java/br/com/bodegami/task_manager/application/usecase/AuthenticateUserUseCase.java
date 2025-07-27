package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.security.LoginUserDTO;
import br.com.bodegami.task_manager.security.RecoveryJwtTokenDTO;

public interface AuthenticateUserUseCase {
    RecoveryJwtTokenDTO execute(LoginUserDTO loginUserDto);
}
