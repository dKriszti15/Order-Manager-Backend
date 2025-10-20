package edu.bbte.idde.dkim2226.spring.service.exceptions;

public class NoOrdersFoundException extends Exception {
    private final String info;

    public NoOrdersFoundException(String message, Throwable cause) {
        super(message, cause);
        this.info = message;
    }

    public String getInfo() {
        return info;
    }
}
