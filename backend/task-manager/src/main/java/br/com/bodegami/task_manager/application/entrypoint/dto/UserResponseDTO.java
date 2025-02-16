package br.com.bodegami.task_manager.application.entrypoint.dto;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name
) {
}
