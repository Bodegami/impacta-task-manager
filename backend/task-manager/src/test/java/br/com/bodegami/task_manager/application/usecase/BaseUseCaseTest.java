package br.com.bodegami.task_manager.application.usecase;

import br.com.bodegami.task_manager.domain.mapper.TaskMapper;
import br.com.bodegami.task_manager.domain.service.TaskCommentService;
import br.com.bodegami.task_manager.domain.service.TaskService;
import br.com.bodegami.task_manager.domain.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUseCaseTest {
    
    @Mock
    protected UserService userService;

    @Mock
    protected TaskService taskService;

    @Mock
    protected TaskCommentService taskCommentService;

    @Mock
    protected TaskMapper taskMapper;
    
}
