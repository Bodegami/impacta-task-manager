package br.com.bodegami.task_manager.security;

import lombok.Getter;

@Getter
public enum RoleName {
    ROLE_CUSTOMER(1L, "ROLE_CUSTOMER"),
    ROLE_ADMINISTRATOR(2L, "ROLE_ADMINISTRATOR");

    private final Long id;
    private final String name;

    RoleName(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
