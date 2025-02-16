package br.com.bodegami.task_manager.domain.service.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.domain.entity.User;
import br.com.bodegami.task_manager.domain.exception.DatabaseIntegrityViolation;
import br.com.bodegami.task_manager.domain.exception.UserNotFoundException;
import br.com.bodegami.task_manager.domain.service.UserService;
import br.com.bodegami.task_manager.domain.mapper.UserMapper;
import br.com.bodegami.task_manager.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_ID = "User not found - ID: %s";
    private static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponseDTO create(CreateUserRequestDTO requestDTO) {

        try {

            //TODO - Create a annotation to validate if the email already exists
            userRepository.findByEmail(requestDTO.email())
                    .ifPresent(user -> {
                        throw new DatabaseIntegrityViolation(EMAIL_ALREADY_EXISTS);
                    });

            User user = userMapper.toDomain(requestDTO);

            User userSaved = userRepository.save(user);

            return userMapper.toCreateResponse(userSaved);
        } catch (Exception ex) {
            throw handlerException(ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {

        try {
            return userRepository.findAll().stream()
                    .map(userMapper::toUserResponse)
                    .toList();

        } catch (Exception ex) {
            throw handlerException(ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailsResponseDTO findById(UUID id) {

        try {
            return userRepository.findById(id)
                    .map(userMapper::toUserDetailsResponse)
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ID.formatted(id)));
        } catch (Exception ex) {
            throw handlerException(ex);
        }
    }

    @Override
    @Transactional
    public UserDetailsResponseDTO update(UUID id, UpdateUserRequestDTO request) {

        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ID.formatted(id)));

            user = userMapper.toUpdateDomain(user, request);

            User userUpdated = userRepository.save(user);

            return userMapper.toUserDetailsResponse(userUpdated);
        } catch (Exception ex) {
            throw handlerException(ex);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw handlerException(ex);
        }
    }

    private RuntimeException handlerException(Exception ex) {
        if (ex instanceof UserNotFoundException) {
            return (UserNotFoundException) ex;
        }
        else if (ex instanceof DatabaseIntegrityViolation) {
            return (DatabaseIntegrityViolation) ex;
        }
        else {
            return new RuntimeException(ex.getMessage());
        }
    }
}
