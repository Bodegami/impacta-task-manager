package br.com.bodegami.task_manager.domain.service.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.domain.entity.User;
import br.com.bodegami.task_manager.domain.exception.DatabaseIntegrityViolation;
import br.com.bodegami.task_manager.domain.exception.UserNotFoundException;
import br.com.bodegami.task_manager.domain.service.UserService;
import br.com.bodegami.task_manager.domain.mapper.UserMapper;
import br.com.bodegami.task_manager.infrastructure.repository.UserRepository;
import br.com.bodegami.task_manager.security.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final SecurityConfiguration securityConfiguration;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository,
                           AuthenticationManager authenticationManager, JwtTokenService jwtTokenService,
                           SecurityConfiguration securityConfiguration) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.securityConfiguration = securityConfiguration;
    }

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
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

            String encodedPassword = getEncodedPassword(requestDTO);

            User user = userMapper.toDomain(requestDTO, encodedPassword);

            User userSaved = userRepository.save(user);
            System.out.println(userSaved.getRoles().get(0).getName());

            return userMapper.toCreateResponse(userSaved);
        } catch (Exception ex) {
            throw handlerException(ex);
        }
    }

    private String getEncodedPassword(CreateUserRequestDTO requestDTO) {
        return securityConfiguration.passwordEncoder().encode(requestDTO.password());
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
