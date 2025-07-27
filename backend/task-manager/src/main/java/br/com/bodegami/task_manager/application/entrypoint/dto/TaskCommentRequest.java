package br.com.bodegami.task_manager.application.entrypoint.dto;

public record TaskCommentRequest(
        String taskId,
        String comment
) {
}
