package br.com.bodegami.task_manager.application.entrypoint.dto;

public record CreateUserRequestDTO(
        String name,
        String email,
        String password
) {
}
