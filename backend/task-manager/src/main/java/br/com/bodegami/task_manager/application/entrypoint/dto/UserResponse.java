package br.com.bodegami.task_manager.application.entrypoint.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name
) {
}
