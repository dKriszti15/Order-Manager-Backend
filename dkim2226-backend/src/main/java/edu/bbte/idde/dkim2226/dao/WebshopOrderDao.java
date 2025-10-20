package edu.bbte.idde.dkim2226.dao;

import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;

import java.util.Collection;

public interface WebshopOrderDao {
    Collection<WebshopOrder> findAll() throws RepositoryException;

    void create(WebshopOrder order) throws RepositoryException;

    void update(WebshopOrder entity) throws RepositoryException;

    void delete(Long id) throws RepositoryException;

    WebshopOrder findById(Long id) throws RepositoryException;
}
