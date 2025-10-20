package edu.bbte.idde.dkim2226.spring.dao.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IdAlreadyExistsException extends Exception {
    private final String info;

    public IdAlreadyExistsException(String info) {
        super(info);
        this.info = info;
    }

    public IdAlreadyExistsException(String info, Throwable cause) {
        super(info, cause);
        this.info = info;
    }
}
