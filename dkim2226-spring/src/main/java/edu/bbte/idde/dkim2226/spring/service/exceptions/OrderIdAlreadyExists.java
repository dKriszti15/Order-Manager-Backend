package edu.bbte.idde.dkim2226.spring.service.exceptions;

public class OrderIdAlreadyExists extends Exception {
    private final String info;

    public OrderIdAlreadyExists(String ex) {
        super(ex);
        this.info = ex;
    }

    public String getInfo() {
        return info;
    }
}
