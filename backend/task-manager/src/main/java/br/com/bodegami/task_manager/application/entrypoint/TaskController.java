package br.com.bodegami.task_manager.application.entrypoint;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.application.usecase.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final SearchTasksUseCase searchTasksUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetAllTasksUseCase getAllTasksUseCase;
    private final GetTaskCommentsUseCase getTaskCommentsUseCase;
    private final AddTaskCommentUseCase addTaskCommentUseCase;
    private final UserService userService;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            GetTaskByIdUseCase getTaskByIdUseCase,
            SearchTasksUseCase searchTasksUseCase,
            UpdateTaskUseCase updateTaskUseCase,
            DeleteTaskUseCase deleteTaskUseCase,
            GetAllTasksUseCase getAllTasksUseCase,
            GetTaskCommentsUseCase getTaskCommentsUseCase,
            AddTaskCommentUseCase addTaskCommentUseCase,
            UserService userService) {
        this.createTaskUseCase = createTaskUseCase;
        this.getTaskByIdUseCase = getTaskByIdUseCase;
        this.searchTasksUseCase = searchTasksUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.getAllTasksUseCase = getAllTasksUseCase;
        this.getTaskCommentsUseCase = getTaskCommentsUseCase;
        this.addTaskCommentUseCase = addTaskCommentUseCase;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateTaskResponseDTO> create(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestBody CreateTaskRequestDTO request) {
        String userId = userService.getUserIdFromToken(httpHeaders);
        CreateTaskResponseDTO response = createTaskUseCase.execute(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDetailsResponse> findTaskById(@PathVariable UUID taskId) {
        TaskDetailsResponse response = getTaskByIdUseCase.execute(taskId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> findAllByParam(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam(required = false) Map<String, String> params) {
        String userId = userService.getUserIdFromToken(httpHeaders);
        List<TaskResponseDTO> result = searchTasksUseCase.execute(userId, params);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDetailsResponse> updateTaskById(
            @PathVariable UUID taskId,
            @RequestBody UpdateTaskRequestDTO request) {
        TaskDetailsResponse response = updateTaskUseCase.execute(taskId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable UUID taskId) {
        deleteTaskUseCase.execute(taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAllTaskByUserId(
            @RequestHeader HttpHeaders httpHeaders) {
        String userId = userService.getUserIdFromToken(httpHeaders);
        List<TaskResponseDTO> response = getAllTasksUseCase.execute(UUID.fromString(userId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<List<TaskCommentResponseDTO>> getComments(@PathVariable UUID taskId) {
        List<TaskCommentResponseDTO> comments = getTaskCommentsUseCase.execute(taskId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comments")
    public ResponseEntity<TaskCommentResponseDTO> addComment(
            @RequestBody TaskCommentRequestDTO dto,
            @RequestHeader HttpHeaders httpHeaders) {
        String userId = userService.getUserIdFromToken(httpHeaders);
        TaskCommentResponseDTO response = addTaskCommentUseCase.execute(
                UUID.fromString(userId), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
