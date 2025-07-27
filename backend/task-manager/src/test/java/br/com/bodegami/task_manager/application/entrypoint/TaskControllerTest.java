package br.com.bodegami.task_manager.application.entrypoint;

import br.com.bodegami.task_manager.application.entrypoint.dto.*;
import br.com.bodegami.task_manager.application.usecase.*;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private CreateTaskUseCase createTaskUseCase;
    @Mock
    private GetTaskByIdUseCase getTaskByIdUseCase;
    @Mock
    private SearchTasksUseCase searchTasksUseCase;
    @Mock
    private UpdateTaskUseCase updateTaskUseCase;
    @Mock
    private DeleteTaskUseCase deleteTaskUseCase;
    @Mock
    private GetAllTasksUseCase getAllTasksUseCase;
    @Mock
    private GetTaskCommentsUseCase getTaskCommentsUseCase;
    @Mock
    private AddTaskCommentUseCase addTaskCommentUseCase;
    @Mock
    private UserService userService;

    @InjectMocks
    private TaskController taskController;

    private final String userId = "550e8400-e29b-41d4-a716-446655440000";
    private final UUID taskId = UUID.fromString("650e8400-e29b-41d4-a716-446655440001");
    private HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        httpHeaders = new HttpHeaders();
        when(userService.getUserIdFromToken(any(HttpHeaders.class))).thenReturn(userId);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        // Arrange
        CreateTaskRequestDTO request = new CreateTaskRequestDTO("Test Task", "Description", "PENDING");
        CreateTaskResponseDTO response = new CreateTaskResponseDTO(taskId.toString(), "Test Task");
        when(createTaskUseCase.execute(any(), any())).thenReturn(response);

        // Act
        ResponseEntity<CreateTaskResponseDTO> result = taskController.create(httpHeaders, request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(createTaskUseCase).execute(eq(request), eq(userId));
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        // Arrange
        TaskDetailsResponse expectedResponse = new TaskDetailsResponse(
                taskId.toString(), "Test Task", "Description", "PENDING", 
                null, null, null, null, null);
        when(getTaskByIdUseCase.execute(taskId)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<TaskDetailsResponse> result = taskController.findTaskById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
        verify(getTaskByIdUseCase).execute(taskId);
    }

    @Test
    void searchTasks_ShouldReturnMatchingTasks() {
        // Arrange
        Map<String, String> params = Map.of("status", "PENDING");
        List<TaskResponseDTO> expectedTasks = List.of(
                new TaskResponseDTO(taskId.toString(), "Task 1", "Desc 1", "PENDING", null, null, null, null)
        );
        when(searchTasksUseCase.execute(userId, params)).thenReturn(expectedTasks);

        // Act
        ResponseEntity<List<TaskResponseDTO>> result = taskController.findAllByParam(httpHeaders, params);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedTasks, result.getBody());
        verify(searchTasksUseCase).execute(userId, params);
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() {
        // Arrange
        UpdateTaskRequestDTO request = new UpdateTaskRequestDTO("Updated Task", "Updated Desc", "IN_PROGRESS");
        TaskDetailsResponse expectedResponse = new TaskDetailsResponse(
                taskId.toString(), "Updated Task", "Updated Desc", "IN_PROGRESS", 
                null, null, null, null, null);
        when(updateTaskUseCase.execute(taskId, request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<TaskDetailsResponse> result = taskController.updateTaskById(taskId, request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
        verify(updateTaskUseCase).execute(taskId, request);
    }

    @Test
    void deleteTask_ShouldReturnNoContent() {
        // Act
        ResponseEntity<Void> result = taskController.deleteTaskById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteTaskUseCase).execute(taskId);
    }

    @Test
    void getAllTasks_ShouldReturnUserTasks() {
        // Arrange
        List<TaskResponseDTO> expectedTasks = List.of(
                new TaskResponseDTO(taskId.toString(), "Task 1", "Desc 1", "PENDING", null, null, null, null)
        );
        when(getAllTasksUseCase.execute(UUID.fromString(userId))).thenReturn(expectedTasks);

        // Act
        ResponseEntity<List<TaskResponseDTO>> result = taskController.findAllTaskByUserId(httpHeaders);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedTasks, result.getBody());
        verify(getAllTasksUseCase).execute(UUID.fromString(userId));
    }

    @Test
    void getComments_ShouldReturnTaskComments() {
        // Arrange
        List<TaskCommentResponseDTO> expectedComments = List.of(
                new TaskCommentResponseDTO(
                        UUID.randomUUID().toString(),
                        "Test comment",
                        "user@example.com",
                        LocalDateTime.now()
                )
        );
        when(getTaskCommentsUseCase.execute(taskId)).thenReturn(expectedComments);

        // Act
        ResponseEntity<List<TaskCommentResponseDTO>> result = taskController.getComments(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedComments, result.getBody());
        verify(getTaskCommentsUseCase).execute(taskId);
    }

    @Test
    void addComment_ShouldReturnCreatedComment() {
        // Arrange
        TaskCommentRequestDTO request = new TaskCommentRequestDTO("New comment", taskId.toString());
        TaskCommentResponseDTO expectedResponse = new TaskCommentResponseDTO(
                UUID.randomUUID().toString(),
                "New comment",
                "user@example.com",
                LocalDateTime.now()
        );
        when(addTaskCommentUseCase.execute(any(UUID.class), any())).thenReturn(expectedResponse);

        // Act
        ResponseEntity<TaskCommentResponseDTO> result = taskController.addComment(request, httpHeaders);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
        verify(addTaskCommentUseCase).execute(UUID.fromString(userId), request);
    }
}
