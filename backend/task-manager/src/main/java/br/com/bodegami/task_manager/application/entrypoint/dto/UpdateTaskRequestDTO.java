package br.com.bodegami.task_manager.application.entrypoint.dto;

public record UpdateTaskRequestDTO(
        String title,
        String description,
        String status,
        String dueDate
) {
}
