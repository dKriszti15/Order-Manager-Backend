package edu.bbte.idde.dkim2226.spring.dao.jpa;

import edu.bbte.idde.dkim2226.spring.dao.WebshopOrderDao;
import edu.bbte.idde.dkim2226.spring.model.Product;
import edu.bbte.idde.dkim2226.spring.model.WebshopOrder;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdNotFoundException;
import edu.bbte.idde.dkim2226.spring.service.exceptions.ProductIdNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@Profile("jpa")
public interface WebshopOrderJpaDao extends JpaRepository<WebshopOrder, Long>, WebshopOrderDao {

    Collection<WebshopOrder> findByCustomerName(String customerName);

    @Override
    default WebshopOrder create(WebshopOrder order) {
        return save(order);
    }

    @Override
    default WebshopOrder update(Long id, WebshopOrder entity) {
        Optional<WebshopOrder> existingOrder = findById(id);

        if (existingOrder.isPresent()) {
            entity.setId(existingOrder.get().getId());
        } else {
            entity.setId(null); // if the order with the requested id does not exist, create a new one
        }

        return save(entity);
    }

    @Override
    default void delete(Long id) {
        if (!existsById(id)) {
            throw new OrderIdNotFoundException("Order with the given ID not found");
        }
        deleteById(id);
    }

    @Override
    default WebshopOrder getById(Long id) {
        return findById(id).orElseThrow(() ->
                new OrderIdNotFoundException("Order with the given ID not found"));
    }

    @Override
    default Collection<Product> getProductsByWebshopOrderId(Long webshopOrderId) {
        WebshopOrder order = findById(webshopOrderId).orElseThrow(() ->
                new OrderIdNotFoundException("Order not found"));
        return order.getProducts();
    }

    @Override
    default Collection<Product> addProductToOrder(Long webshopOrderId, Product product) {
        WebshopOrder order = findById(webshopOrderId).orElseThrow(() ->
                new OrderIdNotFoundException("Order not found"));

        product.setWebshopOrder(order);
        order.getProducts().add(product);

        save(order);

        return order.getProducts();
    }

    @Override
    default void removeProductFromOrder(Long webshopOrderId, Long productId) {
        WebshopOrder order = findById(webshopOrderId).orElseThrow(() ->
                new OrderIdNotFoundException("Order not found"));

        Product productToRemove = order.getProducts().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new ProductIdNotFoundException("Product with the given ID not found in the order"));

        order.getProducts().remove(productToRemove);
        productToRemove.setWebshopOrder(null);

        save(order);
    }
}
