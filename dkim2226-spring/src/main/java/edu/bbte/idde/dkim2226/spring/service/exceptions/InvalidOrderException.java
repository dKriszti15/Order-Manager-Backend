package edu.bbte.idde.dkim2226.spring.service.exceptions;

public class InvalidOrderException extends Exception {
    private final String info;

    public InvalidOrderException(String info) {
        super(info);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
