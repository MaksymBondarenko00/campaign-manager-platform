package com.cpm.authservice.system.exceptions;

public class InvalidEmailException extends Throwable {
    public InvalidEmailException(String message) {
        super(message);
    }
}
