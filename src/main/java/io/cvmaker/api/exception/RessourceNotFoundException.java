package io.cvmaker.api.exception;

public class RessourceNotFoundException extends RuntimeException {
    public RessourceNotFoundException(String message) {
        super(message);
    }
}
