package org.blackbergx9.taskmanagementsystem.exception;

public class DatabaseErrorException extends RuntimeException {

    public DatabaseErrorException(String message) {
        super(message);
    }
}
