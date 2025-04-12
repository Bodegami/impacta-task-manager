package br.com.bodegami.task_manager.application.entrypoint;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskConstroller {

    private final TaskService service;
    private final UserService userService;

    public TaskConstroller(TaskService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateTaskResponseDTO> create(@RequestHeader HttpHeaders httpHeaders,
                                                        @RequestBody CreateTaskRequestDTO request) {

        String userId = userService.getUserIdFromToken(httpHeaders);
        CreateTaskResponseDTO response = service.create(request, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDetailsResponse> findTaskById(@PathVariable UUID taskId) {
        TaskDetailsResponse response = service.findByTaskId(taskId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> findAllByParam(@RequestHeader HttpHeaders httpHeaders,
                                               @RequestParam(required = false) Map<String,String> params) {

        String userId = userService.getUserIdFromToken(httpHeaders);

        List<TaskResponseDTO> result = service.findAllByParams(userId, params);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDetailsResponse> updateTaskById(@PathVariable UUID taskId, @RequestBody UpdateTaskRequestDTO request) {
        TaskDetailsResponse response = service.updateTask(taskId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable UUID taskId) {
        service.deleteTaskById(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAllTaskByUserId(@RequestHeader HttpHeaders httpHeaders) {
        String userId = userService.getUserIdFromToken(httpHeaders);

        List<TaskResponseDTO> response = service.findAllByUserId(UUID.fromString(userId));
        return ResponseEntity.ok(response);
    }

}
