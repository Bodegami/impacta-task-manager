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

    Optional<List<Task>> findAllByUserIdAndTitle(UUID userId, String titulo);

    Optional<List<Task>> findAllByUserIdAndStatus(UUID userId, String status);

    Optional<List<Task>> findAllByUserIdAndDescriptionContaining(UUID userId, String descricao);
}
