package br.com.bodegami.task_manager.infrastructure.repository;

import br.com.bodegami.task_manager.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByUserId(UUID userId);

    List<Task> findAllByUserIdAndTitleIgnoreCase(UUID userId, String titulo);

    List<Task> findAllByUserIdAndStatusIgnoreCase(UUID userId, String status);

    List<Task> findAllByUserIdAndDescriptionContainingIgnoreCase(UUID userId, String descricao);
}
