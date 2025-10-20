package edu.bbte.idde.dkim2226.spring.service.exceptions;

public class ProductIdNotFoundException extends RuntimeException {
    public ProductIdNotFoundException(String message) {
        super(message);
    }
}
