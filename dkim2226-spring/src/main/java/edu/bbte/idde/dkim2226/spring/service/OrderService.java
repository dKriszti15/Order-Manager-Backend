package edu.bbte.idde.dkim2226.spring.service;

import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdAlreadyExistsException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdNotFoundException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.spring.dto.ProductInDTO;
import edu.bbte.idde.dkim2226.spring.dto.ProductOutDTO;
import edu.bbte.idde.dkim2226.spring.dto.WSOrderInDTO;
import edu.bbte.idde.dkim2226.spring.dto.WSOrderOutDTO;
import edu.bbte.idde.dkim2226.spring.service.exceptions.InvalidOrderException;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdAlreadyExists;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdNotFoundException;

import java.util.Collection;

public interface OrderService {
    Collection<WSOrderOutDTO> findAllOrders()
            throws RepositoryException;

    Collection<WSOrderOutDTO> addOrder(WSOrderInDTO order)
            throws InvalidOrderException,
            OrderIdAlreadyExists, RepositoryException;

    Collection<WSOrderOutDTO> removeOrder(Long orderId)
            throws OrderIdNotFoundException, RepositoryException;

    WSOrderOutDTO updateOrder(Long id, WSOrderInDTO order)
            throws InvalidOrderException,
            OrderIdNotFoundException, RepositoryException, IdAlreadyExistsException;

    WSOrderOutDTO findOrderById(Long orderId)
            throws OrderIdNotFoundException, RepositoryException;

    Collection<WSOrderOutDTO> findOrdersByCustomerName(String customerName)
            throws RepositoryException;

    Collection<ProductOutDTO> findProductsByWebshopOrderId(Long webshopOrderId)
            throws RepositoryException, IdNotFoundException;

    Collection<ProductOutDTO> addProductToOrder(Long webshopOrderId, ProductInDTO product)
            throws RepositoryException, IdNotFoundException, IdAlreadyExistsException;

    void removeProductFromOrder(Long webshopOrderId, Long productId)
            throws IdAlreadyExistsException, RepositoryException, IdNotFoundException;
}
