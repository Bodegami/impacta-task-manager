package br.com.bodegami.task_manager.application.entrypoint.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskCommentResponseDTO(
        UUID id,
        String comment,
        String userName,
        LocalDateTime createdAt
) {

}
