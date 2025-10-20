package edu.bbte.idde.dkim2226.service;

import edu.bbte.idde.dkim2226.dao.DaoFactory;
import edu.bbte.idde.dkim2226.dao.WebshopOrderDao;

import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.exceptions.InvalidOrderException;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdAlreadyExists;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdNotFoundException;
import edu.bbte.idde.dkim2226.service.utils.ValidateOrderServiceAttributes;

import java.util.Collection;
import java.util.Date;

public class OrderValidationService implements OrderService {

    private final DaoFactory daoFactory = DaoFactory.getInstance("jdbc");
    private final WebshopOrderDao webshopOrderDao = daoFactory.getWebshopOrderDao();
    private final ValidateOrderServiceAttributes validateOrderServiceAttributes = new ValidateOrderServiceAttributes();

    @Override
    public Collection<WebshopOrder> findAllOrders() throws RepositoryException {
        return webshopOrderDao.findAll();
    }

    @Override
    public void addOrder(WebshopOrder order) throws InvalidOrderException, OrderIdAlreadyExists {
        if (order.getOrderDate().after(new Date())) {
            throw new InvalidOrderException("Order date in the future");
        }
        try {
            validateOrderServiceAttributes.isValid(order);
            webshopOrderDao.create(order);
        } catch (RepositoryException ex) {
            throw new OrderIdAlreadyExists(ex.getMessage());
        }
    }

    @Override
    public void removeOrder(Long orderId) throws OrderIdNotFoundException {
        try {
            webshopOrderDao.delete(orderId);
        } catch (RepositoryException ex) {
            throw new OrderIdNotFoundException(ex.getMessage());
        }
    }

    @Override
    public void updateOrder(WebshopOrder order) throws OrderIdNotFoundException, InvalidOrderException {
        if (order.getOrderDate().after(new Date())) {
            throw new InvalidOrderException("Order date in the future");
        }
        try {
            validateOrderServiceAttributes.isValid(order);
            webshopOrderDao.update(order);
        } catch (RepositoryException ex) {
            throw new OrderIdNotFoundException(ex.getMessage());
        }
    }

    @Override
    public WebshopOrder findOrderById(Long orderId) throws OrderIdNotFoundException {
        WebshopOrder webshopOrder;
        try {
            webshopOrder = webshopOrderDao.findById(orderId);
        } catch (RepositoryException ex) {
            throw new OrderIdNotFoundException(ex.getMessage());
        }
        return webshopOrder;
    }

}
