package com.naib.taskmanager.model;

import java.util.Arrays;

public enum  TaskStatus {
    OPEN("open"), IN_PROGRESS("in progress"), CLOSED("closed");
    private String value;

    TaskStatus(String value) {
        this.value= value;
    }

    public String getValue() {
        return value;
    }
    public static TaskStatus find(String val){
        return Arrays.stream(TaskStatus.values())
                .filter(e -> e.value.equals(val))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", val)));
//        return TaskStatus.OPEN;
    }
}
