package br.com.bodegami.task_manager.domain.entity;

public enum TaskSearchParam {
    TASK_ID("taskId"),
    TITLE("titulo"),
    STATUS("status"),
    DESCRIPTION("descricao"),
    EMPTY("");

    private final String key;

    TaskSearchParam(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static TaskSearchParam fromKey(String key) {
        for (TaskSearchParam param : values()) {
            if (param.getKey().equalsIgnoreCase(key)) {
                return param;
            }
        }
        return EMPTY;
    }
}
