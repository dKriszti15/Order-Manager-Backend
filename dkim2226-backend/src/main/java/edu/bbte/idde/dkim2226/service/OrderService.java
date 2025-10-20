package edu.bbte.idde.dkim2226.service;

import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.exceptions.InvalidOrderException;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdAlreadyExists;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdNotFoundException;

import java.util.Collection;

public interface OrderService {
    Collection<WebshopOrder> findAllOrders() throws RepositoryException;

    void addOrder(WebshopOrder order) throws InvalidOrderException, OrderIdAlreadyExists;

    void removeOrder(Long orderId) throws OrderIdNotFoundException;

    void updateOrder(WebshopOrder order) throws InvalidOrderException, OrderIdNotFoundException;

    WebshopOrder findOrderById(Long orderId) throws OrderIdNotFoundException;
}
