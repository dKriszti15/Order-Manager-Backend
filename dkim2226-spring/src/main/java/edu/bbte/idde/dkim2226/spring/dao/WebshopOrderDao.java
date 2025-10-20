package edu.bbte.idde.dkim2226.spring.dao;


import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdAlreadyExistsException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdNotFoundException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.spring.model.Product;
import edu.bbte.idde.dkim2226.spring.model.WebshopOrder;

import java.util.Collection;

public interface WebshopOrderDao {
    Iterable<WebshopOrder> findAll() throws RepositoryException;

    WebshopOrder create(WebshopOrder order) throws RepositoryException, IdAlreadyExistsException;

    WebshopOrder update(Long id, WebshopOrder entity) throws RepositoryException, IdAlreadyExistsException;

    void delete(Long id) throws RepositoryException, IdNotFoundException;

    WebshopOrder getById(Long id) throws RepositoryException, IdNotFoundException;

    Collection<WebshopOrder> getByCustomerName(String customerName) throws RepositoryException;

    Collection<Product> getProductsByWebshopOrderId(Long webshopOrderId)
            throws RepositoryException, IdNotFoundException;

    Collection<Product> addProductToOrder(Long webshopOrderId, Product product)
            throws RepositoryException, IdNotFoundException, IdAlreadyExistsException;

    void removeProductFromOrder(Long webshopOrderId, Long productId)
            throws IdNotFoundException, IdAlreadyExistsException, RepositoryException;
}
