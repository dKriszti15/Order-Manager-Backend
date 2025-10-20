package edu.bbte.idde.dkim2226.spring.service;

import edu.bbte.idde.dkim2226.spring.dao.WebshopOrderDao;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdAlreadyExistsException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdNotFoundException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.spring.dto.ProductInDTO;
import edu.bbte.idde.dkim2226.spring.dto.ProductOutDTO;
import edu.bbte.idde.dkim2226.spring.dto.WSOrderInDTO;
import edu.bbte.idde.dkim2226.spring.dto.WSOrderOutDTO;
import edu.bbte.idde.dkim2226.spring.mapper.ProductMapper;
import edu.bbte.idde.dkim2226.spring.mapper.WSOrderMapper;
import edu.bbte.idde.dkim2226.spring.model.WebshopOrder;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdAlreadyExists;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdNotFoundException;
import edu.bbte.idde.dkim2226.spring.service.exceptions.ProductIdNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderValidationService implements OrderService {
    @Autowired
    private WebshopOrderDao webshopOrderDao;

    @Autowired
    private WSOrderMapper wsOrderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Collection<WSOrderOutDTO> findAllOrders()
            throws RepositoryException {
        return wsOrderMapper.modelsToOutDtos(webshopOrderDao.findAll());
    }

    @Override
    public Collection<WSOrderOutDTO> addOrder(WSOrderInDTO order)
            throws OrderIdAlreadyExists, RepositoryException {
        try {
            WebshopOrder webshopOrder = wsOrderMapper.dtoToModel(order);

            webshopOrderDao.create(webshopOrder);

            return findAllOrders();
        } catch (IdAlreadyExistsException ex) {
            throw new OrderIdAlreadyExists(ex.getMessage());
        }
    }

    @Override
    public Collection<WSOrderOutDTO> removeOrder(Long orderId)
            throws OrderIdNotFoundException, RepositoryException {
        try {
            webshopOrderDao.delete(orderId);

            return findAllOrders();
        } catch (IdNotFoundException e) {
            throw new OrderIdNotFoundException(e.getMessage());
        }
    }

    @Override
    public WSOrderOutDTO updateOrder(Long id, WSOrderInDTO order)
            throws OrderIdNotFoundException, RepositoryException {
        try {
            WebshopOrder webshopOrder = wsOrderMapper.dtoToModel(order);

            webshopOrderDao.update(id, webshopOrder);

            return wsOrderMapper.modelToOutDto(webshopOrder);
        } catch (IdAlreadyExistsException ex) {
            throw new OrderIdNotFoundException(ex.getMessage());
        }
    }

    @Override
    public WSOrderOutDTO findOrderById(Long orderId)
            throws OrderIdNotFoundException, RepositoryException {
        WebshopOrder webshopOrder;
        try {
            webshopOrder = webshopOrderDao.getById(orderId);
        } catch (IdNotFoundException ex) {
            throw new OrderIdNotFoundException(ex.getMessage());
        }
        return wsOrderMapper.modelToOutDto(webshopOrder);
    }

    @Override
    public Collection<WSOrderOutDTO> findOrdersByCustomerName(String customerName)
            throws RepositoryException {
        return wsOrderMapper.modelsToOutDtos(webshopOrderDao.getByCustomerName(customerName));
    }

    @Override
    public Collection<ProductOutDTO> findProductsByWebshopOrderId(Long webshopOrderId)
            throws RepositoryException, IdNotFoundException {
        return productMapper.modelsToDtos(webshopOrderDao.getProductsByWebshopOrderId(webshopOrderId));
    }

    @Override
    public Collection<ProductOutDTO> addProductToOrder(Long webshopOrderId, ProductInDTO product)
            throws RepositoryException, IdNotFoundException, IdAlreadyExistsException {
        return productMapper.modelsToDtos(webshopOrderDao.addProductToOrder(webshopOrderId,
                productMapper.dtoToModel(product)));
    }

    @Override
    public void removeProductFromOrder(Long webshopOrderId, Long productId)
            throws IdAlreadyExistsException,
            RepositoryException, IdNotFoundException, ProductIdNotFoundException {
        webshopOrderDao.removeProductFromOrder(webshopOrderId, productId);
    }

}
