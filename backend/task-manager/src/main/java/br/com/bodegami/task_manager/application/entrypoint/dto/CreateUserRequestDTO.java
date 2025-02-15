package br.com.bodegami.task_manager.controller.dto;

public record CreateUserRequestDTO(
        String name,
        String email,
        String password
) {
}
