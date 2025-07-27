package br.com.bodegami.task_manager.application.entrypoint.dto;

public record CreateTaskRequest(
        String title,
        String description,
        String dueDate
) {
}
