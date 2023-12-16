package com.project.consolecrud.model;

public enum PostStatus {
    ACTIVE("ACTIVE"),
    UNDER_REVIEW("UNDER_REVIEW"),
    DELETED("DELETED");

    private String value;

    PostStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
