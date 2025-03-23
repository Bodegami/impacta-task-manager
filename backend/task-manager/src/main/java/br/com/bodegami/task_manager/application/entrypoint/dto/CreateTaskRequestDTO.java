package br.com.bodegami.task_manager.application.entrypoint.dto;

public record CreateTaskRequestDTO(
        String title,
        String description,
        String status,
        String dueDate
) {
}
