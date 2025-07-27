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
        httpHeaders = mock(HttpHeaders.class);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        // Arrange
        var request = new CreateTaskRequest("Test Task", "Description", "PENDING");
        var response = new CreateTaskResponse(taskId, "Test Task", "Description", "PENDING",
                UUID.fromString(userId), null, null);

        when(createTaskUseCase.execute(request, httpHeaders)).thenReturn(response);

        // Act
        ResponseEntity<CreateTaskResponse> result = taskController.create(httpHeaders, request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(createTaskUseCase).execute(request, httpHeaders);
    }

    @Test
    void getTaskById_ShouldReturnTask() {
        // Arrange
        TaskDetailsResponse expectedResponse = new TaskDetailsResponse(
                taskId, "Test Task", "Description", "PENDING",
                null, null, null);
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
        List<TaskResponse> expectedTasks = List.of(
                new TaskResponse(taskId, "Task 1", "PENDING")
        );

        when(searchTasksUseCase.execute(httpHeaders, params)).thenReturn(expectedTasks);

        // Act
        ResponseEntity<List<TaskResponse>> result = taskController.findAllByParam(httpHeaders, params);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedTasks, result.getBody());
        verify(searchTasksUseCase).execute(httpHeaders, params);
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() {
        // Arrange
        var request = new UpdateTaskRequest("Updated Task", "Updated Desc", "IN_PROGRESS", "1990-01-01");
        var expectedResponse = new TaskDetailsResponse(taskId, "Updated Task", "Updated Desc", "IN_PROGRESS",
                null, null, null);
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
        List<TaskResponse> expectedTasks = List.of(
                new TaskResponse(taskId, "Task 1", "PENDING")
        );

        when(getAllTasksUseCase.execute(httpHeaders)).thenReturn(expectedTasks);

        // Act
        ResponseEntity<List<TaskResponse>> result = taskController.findAllTaskByUserId(httpHeaders);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedTasks, result.getBody());
        verify(getAllTasksUseCase).execute(httpHeaders);
    }

    @Test
    void getComments_ShouldReturnTaskComments() {
        // Arrange

        List<TaskCommentResponse> expectedComments = List.of(
                new TaskCommentResponse(
                        UUID.randomUUID(),
                        "Test comment",
                        "user@example.com",
                        LocalDateTime.now()
                )
        );
        when(getTaskCommentsUseCase.execute(taskId)).thenReturn(expectedComments);

        // Act
        ResponseEntity<List<TaskCommentResponse>> result = taskController.getComments(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedComments, result.getBody());
        verify(getTaskCommentsUseCase).execute(taskId);
    }

    @Test
    void addComment_ShouldReturnCreatedComment() {
        // Arrange
        TaskCommentRequest request = new TaskCommentRequest("New comment", taskId.toString());
        TaskCommentResponse expectedResponse = new TaskCommentResponse(
                UUID.randomUUID(),
                "New comment",
                "user@example.com",
                LocalDateTime.now()
        );

        when(addTaskCommentUseCase.execute(httpHeaders, request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<TaskCommentResponse> result = taskController.addComment(request, httpHeaders);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(expectedResponse, result.getBody());
        verify(addTaskCommentUseCase).execute(httpHeaders, request);
    }
}
