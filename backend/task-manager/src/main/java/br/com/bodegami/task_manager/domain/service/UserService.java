package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.security.LoginUserDTO;
import br.com.bodegami.task_manager.security.RecoveryJwtTokenDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    CreateUserResponseDTO create(CreateUserRequestDTO requestDTO);

    List<UserResponseDTO> findAll();

    UserDetailsResponseDTO findById(UUID id);

    UserDetailsResponseDTO update(UUID id, UpdateUserRequestDTO request);

    void delete(UUID id);

    RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDto);
}
