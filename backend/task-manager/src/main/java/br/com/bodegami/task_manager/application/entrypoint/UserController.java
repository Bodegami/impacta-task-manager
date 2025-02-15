package br.com.bodegami.task_manager.application.entrypoint;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserDetailsResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.UserResponseDTO;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> create(@RequestBody CreateUserRequestDTO request) {

        CreateUserResponseDTO response = service.create(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {

        List<UserResponseDTO> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponseDTO> findById(@PathVariable UUID id) {

        UserDetailsResponseDTO response = service.findById(id);

        return ResponseEntity.ok(response);
    }

}
