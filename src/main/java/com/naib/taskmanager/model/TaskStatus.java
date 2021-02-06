package com.naib.taskmanager.model;

public enum  TaskStatus {
    OPEN("open"), IN_PROGRESS("in progress"), CLOSED("closed");
    private String status;

    TaskStatus(String status) {
        this.status= status;
    }

    public String getStatus() {
        return status;
    }
}
