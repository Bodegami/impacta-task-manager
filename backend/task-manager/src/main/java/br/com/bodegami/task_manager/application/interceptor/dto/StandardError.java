package br.com.bodegami.task_manager.application.interceptor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
public record StandardError(
        String timestamp,
        Integer status,
        String path,
        String error,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<FieldError> errors
) {
}

