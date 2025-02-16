package br.com.bodegami.task_manager.application.entrypoint.dto;

import java.util.UUID;

public record CreateUserResponseDTO(
        UUID id,
        String name,
        String email,
        String createdAt
) {
}
