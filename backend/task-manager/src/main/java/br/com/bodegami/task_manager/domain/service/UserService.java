package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.security.LoginUserDTO;
import br.com.bodegami.task_manager.security.RecoveryJwtTokenDTO;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

public interface UserService {

    CreateUserResponse create(CreateUserRequest requestDTO);

    List<UserResponse> findAll();

    UserDetailsResponse findById(UUID id);

    UserDetailsResponse update(UUID id, UpdateUserRequest request);

    void delete(UUID id);

    RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDto);

    String getUserIdFromToken(HttpHeaders httpHeaders);
}
