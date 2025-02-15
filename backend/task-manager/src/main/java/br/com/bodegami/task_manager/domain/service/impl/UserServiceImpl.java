package br.com.bodegami.task_manager.domain.service.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponseDTO;
import br.com.bodegami.task_manager.domain.entity.User;
import br.com.bodegami.task_manager.domain.service.UserService;
import br.com.bodegami.task_manager.domain.mapper.UserMapper;
import br.com.bodegami.task_manager.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
            User user = userMapper.toDomain(requestDTO);

            User userSaved = userRepository.save(user);

            return userMapper.toCreateResponse(userSaved);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<UserResponseDTO> findAll() {

        try {
            return userRepository.findAll().stream()
                    .map(userMapper::toUserResponse)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserDetailsResponseDTO findById(UUID id) {

        try {
            return userRepository.findById(id)
                    .map(userMapper::toUserDetailsResponse)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
