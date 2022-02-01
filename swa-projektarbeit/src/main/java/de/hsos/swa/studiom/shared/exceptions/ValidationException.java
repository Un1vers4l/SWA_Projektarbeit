package de.hsos.swa.studiom.shared.exceptions;

public class ValidationException extends Exception {
    private final String message;

    public ValidationException(String reason) {
        this.message = reason;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationException: " + message;
    }
}
