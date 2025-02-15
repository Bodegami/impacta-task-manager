package br.com.bodegami.task_manager.application.entrypoint.dto;

public record UpdateUserRequestDTO(
        String name,
        String email
) {
}
