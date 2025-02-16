package br.com.bodegami.task_manager.domain.exception;

public class DatabaseIntegrityViolation extends RuntimeException {

    public DatabaseIntegrityViolation(String message) {
        super(message);
    }
}
