package edu.bbte.idde.dkim2226.spring.dao.mem;

import edu.bbte.idde.dkim2226.spring.dao.WebshopOrderDao;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdAlreadyExistsException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdNotFoundException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.spring.model.Product;
import edu.bbte.idde.dkim2226.spring.model.WebshopOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("mem")
public class WebshopOrderMemDao implements WebshopOrderDao {
    protected Map<Long, WebshopOrder> entities = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(WebshopOrderMemDao.class);

    private Long currentWSOrderId = 0L;

    private synchronized Long generateNewId() {
        return ++currentWSOrderId;
    }

    @Override
    public Iterable<WebshopOrder> findAll() {
        return entities.values();
    }

    @Override
    public WebshopOrder create(WebshopOrder entity) throws IdAlreadyExistsException {

        if (entity.getId() == null) {
            Long id = generateNewId();
            entity.setId(id);
        }

        entities.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public WebshopOrder update(Long id, WebshopOrder entity) throws IdAlreadyExistsException {

        entity.setId(id);

        if (!entities.containsKey(id)) {
            logger.error("Entity with id {} does not exist, so it will be created ASAP", entity.getId());
            entity.setId(id);
            create(entity);
        }
        logger.info("Entity with id {} updated succesfully", entity.getId());
        entities.put(id, entity);
        return entity;
    }

    @Override
    public void delete(Long id) throws IdNotFoundException {
        if (!entities.containsKey(id)) {
            logger.error("Entity with this id: {} does not exist", id);
            throw new IdNotFoundException("ID does not exist");
        }
        logger.info("Entity with id {} deleted succesfully", id);
        entities.remove(id);
    }

    @Override
    public WebshopOrder getById(Long id) throws IdNotFoundException {
        if (!entities.containsKey(id)) {
            logger.error("Id {} not found", id);
            throw new IdNotFoundException("ID not exist");
        }

        return entities.get(id);
    }

    @Override
    public Collection<WebshopOrder> getByCustomerName(String customerName) {
        return entities.values().stream()
                .filter(order -> order.getCustomerName().equals(customerName))
                .toList();
    }

    @Override
    public Collection<Product> getProductsByWebshopOrderId(Long webshopOrderId) throws RepositoryException {

        WebshopOrder order = entities.get(webshopOrderId);
        if (order != null) {
            return order.getProducts();
        }
        return List.of();
    }

    @Override
    public Collection<Product> addProductToOrder(Long webshopOrderId, Product product)
            throws RepositoryException, IdNotFoundException, IdAlreadyExistsException {
        WebshopOrder order = entities.get(webshopOrderId);

        if (order == null) {
            throw new IdNotFoundException("Order not found");
        }

        product.setWebshopOrder(order);

        if (order.getProducts() == null) {
            order.setProducts(new ArrayList<>());
        }

        Long nextId = order.getProducts().stream()
                .map(Product::getId)
                .max(Long::compare)
                .orElse(0L) + 1;

        product.setId(nextId);

        order.getProducts().add(product);

        update(webshopOrderId, order);

        return order.getProducts();
    }


    @Override
    public void removeProductFromOrder(Long webshopOrderId, Long productId)
            throws IdNotFoundException, IdAlreadyExistsException {
        WebshopOrder order = entities.get(webshopOrderId);

        if (order == null) {
            throw new IdNotFoundException("Order not found");
        }

        order.getProducts().removeIf(product -> product.getId().equals(productId));

        update(webshopOrderId, order);
    }
}
