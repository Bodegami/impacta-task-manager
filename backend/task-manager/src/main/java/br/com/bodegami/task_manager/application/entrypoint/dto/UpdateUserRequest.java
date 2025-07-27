package br.com.bodegami.task_manager.application.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotNull @NotBlank @Size(min = 3, max = 100)
        String name,
        @NotNull @NotBlank @Size(min = 10, max = 150)
        String email
) {
}
