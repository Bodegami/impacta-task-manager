package br.com.bodegami.task_manager.security;

public record LoginUserDTO(
        String email,
        String password
) {
}
