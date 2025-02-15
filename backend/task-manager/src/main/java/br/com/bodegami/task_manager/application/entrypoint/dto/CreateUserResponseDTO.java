package br.com.bodegami.task_manager.controller.dto;

public record CreateUserResponseDTO(
        String name,
        String email,
        String createdAt
) {
}
