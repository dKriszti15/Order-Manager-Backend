package edu.bbte.idde.dkim2226.dao.exceptions;

public class NoIdsFoundException extends Exception {

    public NoIdsFoundException(String idListEmpty, Throwable cause) {
        super(idListEmpty, cause);
    }

    public NoIdsFoundException(Throwable cause) {
        super(cause);
    }

    public NoIdsFoundException(String idListEmpty) {
        super(idListEmpty);
    }
}
