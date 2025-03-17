package br.com.bodegami.task_manager.application.entrypoint;

import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskRequestDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.CreateTaskResponseDTO;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskDetailsResponse;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskResponseDTO;
import br.com.bodegami.task_manager.domain.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDetailsResponse> findTaskById(@PathVariable UUID taskId) {
        TaskDetailsResponse response = service.findByTaskId(taskId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable UUID taskId) {
        service.deleteTaskById(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> findAllTaskByUserId(@PathVariable UUID userId) {
        List<TaskResponseDTO> response = service.findAllByUserId(userId);
        return ResponseEntity.ok(response);
    }

}
