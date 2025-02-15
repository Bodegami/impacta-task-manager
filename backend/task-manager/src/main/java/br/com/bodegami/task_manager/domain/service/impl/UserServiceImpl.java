package br.com.bodegami.task_manager.service.impl;

import br.com.bodegami.task_manager.controller.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.controller.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.domain.entity.User;
import br.com.bodegami.task_manager.mapper.UserMapper;
import br.com.bodegami.task_manager.repository.UserRepository;
import br.com.bodegami.task_manager.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public CreateUserResponseDTO create(CreateUserRequestDTO requestDTO) {

        try {
            //Transforma em entidade do dominio
            User user = userMapper.toDomain(requestDTO);

            //Salva no banco de dados
            User userSaved = userRepository.save(user);

            //Transforma em DTO de resposta
            CreateUserResponseDTO response = userMapper.toDTO(userSaved);

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
