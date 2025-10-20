package edu.bbte.idde.dkim2226.service.exceptions;

public class OrderIdNotFoundException extends RuntimeException {
    private final String info;

    public OrderIdNotFoundException(String ex) {
        super(ex);
        this.info = ex;
    }

    public String getInfo() {
        return info;
    }
}
