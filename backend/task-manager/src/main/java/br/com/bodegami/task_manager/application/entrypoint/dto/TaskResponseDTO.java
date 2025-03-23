package br.com.bodegami.task_manager.application.entrypoint.dto;

import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String status
) {
}
