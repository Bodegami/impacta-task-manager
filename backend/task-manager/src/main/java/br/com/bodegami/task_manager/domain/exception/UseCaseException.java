package br.com.bodegami.task_manager.domain.exception;

public class UseCaseException extends RuntimeException {

    public UseCaseException(String message) {
        super(message);
    }

    public UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
