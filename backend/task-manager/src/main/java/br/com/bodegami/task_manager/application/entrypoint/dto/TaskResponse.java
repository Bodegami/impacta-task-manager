package br.com.bodegami.task_manager.application.entrypoint.dto;

import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String status
) {
}
