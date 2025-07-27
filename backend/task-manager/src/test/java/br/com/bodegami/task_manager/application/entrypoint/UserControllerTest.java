package br.com.bodegami.task_manager.application.entrypoint;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.application.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static br.com.bodegami.task_manager.security.RoleName.ROLE_CUSTOMER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private ListUsersUseCase listUsersUseCase;

    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @InjectMocks
    private UserController userController;

    private UUID userId;
    private CreateUserRequestDTO createRequest;
    private CreateUserResponseDTO createResponse;
    private UserResponseDTO userResponse;
    private UserDetailsResponseDTO userDetailsResponse;
    private UpdateUserRequestDTO updateRequest;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        
        createRequest = new CreateUserRequestDTO(
                "John Doe",
                "john.doe@example.com",
                "password123",
                ROLE_CUSTOMER.name()
        );

        createResponse = new CreateUserResponseDTO(
                userId,
                "John Doe",
                "john.doe@example.com",
                "1990-01-01"
        );

        userResponse = new UserResponseDTO(
                userId,
                "John Doe"
        );

        userDetailsResponse = new UserDetailsResponseDTO(
                userId,
                "John Doe",
                "john.doe@example.com",
                LocalDate.of(1990, 1, 1).toString()
        );

        updateRequest = new UpdateUserRequestDTO(
                "John Updated",
                "john.updated@example.com"
        );
    }

    @Test
    void create_ShouldReturnCreatedUser() {
        // Arrange
        when(createUserUseCase.execute(any(CreateUserRequestDTO.class))).thenReturn(createResponse);

        // Act
        ResponseEntity<CreateUserResponseDTO> response = userController.create(createRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createResponse, response.getBody());
        verify(createUserUseCase, times(1)).execute(createRequest);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        // Arrange
        List<UserResponseDTO> users = List.of(userResponse);
        when(listUsersUseCase.execute()).thenReturn(users);

        // Act
        ResponseEntity<List<UserResponseDTO>> response = userController.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(userResponse, response.getBody().get(0));
        verify(listUsersUseCase, times(1)).execute();
    }

    @Test
    void findById_ShouldReturnUser() {
        // Arrange
        when(getUserByIdUseCase.execute(userId)).thenReturn(userDetailsResponse);

        // Act
        ResponseEntity<UserDetailsResponseDTO> response = userController.findById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDetailsResponse, response.getBody());
        verify(getUserByIdUseCase, times(1)).execute(userId);
    }

    @Test
    void update_ShouldReturnUpdatedUser() {
        // Arrange
        when(updateUserUseCase.execute(eq(userId), any(UpdateUserRequestDTO.class)))
                .thenReturn(userDetailsResponse);

        // Act
        ResponseEntity<UserDetailsResponseDTO> response = userController.update(userId, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDetailsResponse, response.getBody());
        verify(updateUserUseCase, times(1)).execute(eq(userId), any(UpdateUserRequestDTO.class));
    }

    @Test
    void delete_ShouldReturnNoContent() {
        // Arrange
        doNothing().when(deleteUserUseCase).execute(userId);

        // Act
        ResponseEntity<Void> response = userController.delete(userId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(deleteUserUseCase, times(1)).execute(userId);
    }
}
