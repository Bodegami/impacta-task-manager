package br.com.bodegami.task_manager.domain.service;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;

import java.util.List;
import java.util.UUID;

public interface UserService {

    CreateUserResponseDTO create(CreateUserRequestDTO requestDTO);

    List<UserResponseDTO> findAll();

    UserDetailsResponseDTO findById(UUID id);

    UserDetailsResponseDTO update(UUID id, UpdateUserRequestDTO request);

    void delete(UUID id);
}
