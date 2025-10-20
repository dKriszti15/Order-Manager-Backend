package edu.bbte.idde.dkim2226.spring.dao.exceptions;

public class RepositoryException extends Exception {
    public RepositoryException(Throwable cause) {
        super(cause);
    }

    public RepositoryException(String info) {
        super(info);
    }

    public RepositoryException(String info, Throwable cause) {
        super(info, cause);
    }
}
