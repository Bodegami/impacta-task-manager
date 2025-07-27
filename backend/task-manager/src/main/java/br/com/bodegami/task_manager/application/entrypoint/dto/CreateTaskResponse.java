package br.com.bodegami.task_manager.application.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTaskResponse(
        UUID id,
        String title,
        String description,
        String status,
        @JsonProperty("user_id")
        UUID userId,
        @JsonProperty("due_date")
        LocalDateTime dueDate,
        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}
