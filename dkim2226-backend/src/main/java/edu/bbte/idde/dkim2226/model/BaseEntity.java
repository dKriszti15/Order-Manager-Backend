package edu.bbte.idde.dkim2226.model;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseEntity implements Serializable {
    protected Long id;

    public BaseEntity() {}

    public BaseEntity(Long id) {
        this.id = id;
    }
}
