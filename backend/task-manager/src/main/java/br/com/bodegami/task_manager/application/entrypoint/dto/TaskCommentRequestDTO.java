package br.com.bodegami.task_manager.application.entrypoint.dto;

public record TaskCommentRequestDTO(
        String taskId,
        String comment
) {
}
