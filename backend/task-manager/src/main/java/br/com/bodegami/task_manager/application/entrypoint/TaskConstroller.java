package br.com.bodegami.task_manager.application.entrypoint;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskConstroller {

    private final TaskService service;

    public TaskConstroller(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreateTaskResponseDTO> create(@RequestBody CreateTaskRequestDTO request) {

        CreateTaskResponseDTO response = service.create(request);

        return ResponseEntity.ok(response);
    }

}
