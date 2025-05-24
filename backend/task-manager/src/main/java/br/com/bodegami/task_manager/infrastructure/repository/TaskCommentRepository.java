package br.com.bodegami.task_manager.infrastructure.repository;

import br.com.bodegami.task_manager.domain.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskCommentRepository extends JpaRepository<TaskComment, UUID> {
    List<TaskComment> findByTaskIdOrderByCreatedAtAsc(UUID taskId);
}

