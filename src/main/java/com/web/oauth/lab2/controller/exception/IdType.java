package com.web.oauth.lab2.controller.exception;

public enum IdType {
    DB_ID,
    USER_ID;

    @Override
    public String toString() {
        switch (this) {
            case DB_ID:
                return "db id";
            case USER_ID:
                return "user id";
            default:
                throw new IllegalArgumentException();
        }
    }
}
