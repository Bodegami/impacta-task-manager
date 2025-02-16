package br.com.bodegami.task_manager.application.interceptor.dto;

public record FieldError(
        String field,
        String message
) {
}
