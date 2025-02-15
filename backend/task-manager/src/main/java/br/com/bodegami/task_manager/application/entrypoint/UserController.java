package br.com.bodegami.task_manager.controller;

import br.com.bodegami.task_manager.controller.dto.CreateUserRequestDTO;
import br.com.bodegami.task_manager.controller.dto.CreateUserResponseDTO;
import br.com.bodegami.task_manager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
