package br.com.bodegami.task_manager.application.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotNull @NotBlank @Size(min = 3, max = 100)
        String name,
        @NotNull @NotBlank @Size(min = 10, max = 150)
        String email,
        @NotNull @NotBlank @Size(min = 5, max = 250)
        String password,
        @JsonInclude(JsonInclude. Include. NON_NULL)
        String role
) {
}
