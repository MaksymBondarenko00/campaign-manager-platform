package com.cpm.authservice.system.exceptions;

public class EmailAlreadyUsedException extends Exception {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
