package br.com.bodegami.task_manager.domain.service.impl;

import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentRequest;
import br.com.bodegami.task_manager.application.entrypoint.dto.TaskCommentResponse;
import br.com.bodegami.task_manager.domain.entity.Task;
import br.com.bodegami.task_manager.domain.entity.TaskComment;
import br.com.bodegami.task_manager.domain.entity.User;
import br.com.bodegami.task_manager.domain.mapper.TaskCommentMapper;
import br.com.bodegami.task_manager.domain.service.TaskCommentService;
import br.com.bodegami.task_manager.infrastructure.repository.TaskCommentRepository;
import br.com.bodegami.task_manager.infrastructure.repository.TaskRepository;
import br.com.bodegami.task_manager.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskCommentServiceImpl implements TaskCommentService {

    @Autowired
    private TaskCommentRepository commentRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskCommentMapper taskCommentMapper;

    public List<TaskCommentResponse> getCommentsByTask(UUID taskId) {
        return commentRepo.findByTaskIdOrderByCreatedAtAsc(taskId).stream()
                .map(taskCommentMapper::toResponseDTO)
                .toList();
    }

    public TaskCommentResponse addComment(UUID userId, TaskCommentRequest dto) {
        if (dto == null) {
            throw new RuntimeException("Payload invalido");
        }
        Task task = taskRepo.findById(UUID.fromString(dto.taskId()))
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        User user = userRepo.findById(task.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        TaskComment comment = taskCommentMapper.toDomain(dto.comment(), user, task);

        TaskComment saved = commentRepo.save(comment);
        return taskCommentMapper.toResponseDTO(saved);
    }
}

