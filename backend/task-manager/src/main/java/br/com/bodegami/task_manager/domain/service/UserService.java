package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    CreateUserResponseDTO create(CreateUserRequestDTO requestDTO);

    List<UserResponseDTO> findAll();

    UserDetailsResponseDTO findById(UUID id);
}
