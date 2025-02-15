package br.com.bodegami.task_manager.service;

import br.com.bodegami.task_manager.controller.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.controller.dto.CreateUserResponseDTO;

public interface UserService {

    CreateUserResponseDTO create(CreateUserRequestDTO requestDTO);

}
