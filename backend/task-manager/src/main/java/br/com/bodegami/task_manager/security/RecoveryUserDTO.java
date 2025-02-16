package br.com.bodegami.task_manager.security;

import java.util.List;

public record RecoveryUserDTO(
        Long id,
        String email,
        List<Role> roles
) {
}
