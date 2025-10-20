package edu.bbte.idde.dkim2226.spring.dao.jdbc;

import edu.bbte.idde.dkim2226.spring.dao.WebshopOrderDao;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdAlreadyExistsException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdNotFoundException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.spring.model.Product;
import edu.bbte.idde.dkim2226.spring.model.WebshopOrder;
import edu.bbte.idde.dkim2226.spring.service.exceptions.ProductIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@Profile("jdbc")
public class WebshopOrderJdbcDao implements WebshopOrderDao {

    @Autowired
    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(WebshopOrderJdbcDao.class);

    @Override
    public Iterable<WebshopOrder> findAll() throws RepositoryException {
        Collection<WebshopOrder> orders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders")) {

            extracted_Orders(resultSet, orders);

            if (orders.isEmpty()) {
                logger.error("No orders found");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Failed to retrieve orders.", e);
        }

        return orders;
    }

    private static void extracted_Orders(ResultSet resultSet, Collection<WebshopOrder> orders) throws SQLException {
        while (resultSet.next()) {
            WebshopOrder order = new WebshopOrder(
                    resultSet.getDate("orderDate"), resultSet.getString("customerName"),
                    resultSet.getString("address"), resultSet.getLong("amountPaid"),
                    resultSet.getString("status"), null);
            order.setId(resultSet.getLong("id"));
            orders.add(order);
        }
    }


    @Override
    public WebshopOrder create(WebshopOrder order) throws IdAlreadyExistsException {
        String query = "INSERT INTO orders (orderDate, address,"
                + " customerName, amountPaid, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, new Date(order.getOrderDate().getTime()));
            statement.setString(2, order.getAddress());
            statement.setString(3, order.getCustomerName());
            statement.setLong(4, order.getAmountPaid());
            statement.setString(5, order.getStatus());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                }
            }

            logger.info("Order created with ID {}", order.getId());
        } catch (SQLException e) {
            logger.warn("Entity with id {} already exists", order.getId());
            throw new IdAlreadyExistsException("Order with ID " + order.getId() + " already exists.", e);
        }
        return order;
    }


    @Override
    public WebshopOrder update(Long id, WebshopOrder order) throws RepositoryException, IdAlreadyExistsException {
        String query = "UPDATE orders SET orderDate = ?, address = ?,"
                + " customerName = ?, amountPaid = ?, status = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, new Date(order.getOrderDate().getTime()));
            statement.setString(2, order.getAddress());
            statement.setString(3, order.getCustomerName());
            statement.setLong(4, order.getAmountPaid());
            statement.setString(5, order.getStatus());
            statement.setLong(6, id);

            order.setId(id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                logger.error("Order with ID {} not found, it will be created ASAP", order.getId());
                create(order);
            }


            logger.info("Order with ID {} updated.", order.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Failed to update order with ID " + order.getId(), e);
        }
        return order;
    }

    @Override
    public void delete(Long id) throws RepositoryException, IdNotFoundException {
        String query = "DELETE FROM orders WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                logger.error("Order with orderID {} not found.", id);
                throw new IdNotFoundException("Order with ID " + id + " not found.");
            }
            logger.info("Order with ID {} deleted succesfully.", id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Failed to delete order with ID " + id, e);
        }
    }

    @Override
    public WebshopOrder getById(Long id) throws RepositoryException, IdNotFoundException {
        WebshopOrder foundOrder = null;
        String query = "SELECT * FROM orders WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                foundOrder = new WebshopOrder(
                        resultSet.getDate("orderDate"), resultSet.getString("customerName"),
                        resultSet.getString("address"), resultSet.getLong("amountPaid"),
                        resultSet.getString("status"), null);
                foundOrder.setId(resultSet.getLong("id"));
            }

            if (foundOrder != null) {
                logger.info("Order with ID {} found.", id);
            } else {
                logger.error("Order with ID {} could not be found.", id);
                throw new IdNotFoundException("Order with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Failed to get order with ID " + id, e);
        }
        return foundOrder;
    }

    @Override
    public Collection<WebshopOrder> getByCustomerName(String customerName) throws RepositoryException {
        Collection<WebshopOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customerName = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customerName);
            ResultSet resultSet = statement.executeQuery();

            extracted_Orders(resultSet, orders);

            if (orders.isEmpty()) {
                logger.error("No orders found for customer {}", customerName);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Failed to retrieve orders for customer " + customerName, e);
        }

        return orders;
    }

    @Override
    public Collection<Product> getProductsByWebshopOrderId(Long webshopOrderId)
            throws RepositoryException, IdNotFoundException {
        Collection<Product> products = new ArrayList<>();

        String query = "SELECT * FROM products WHERE order_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, webshopOrderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getLong("id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getLong("price"));
                    product.setStock(resultSet.getBoolean("stock"));
                    product.setWebshopOrder(getById(webshopOrderId));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to retrieve products for order ID " + webshopOrderId, e);
        }

        return products;
    }


    @Override
    public Collection<Product> addProductToOrder(Long webshopOrderId, Product product)
            throws RepositoryException, IdNotFoundException {
        String query = "INSERT INTO products (name, price, stock, order_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setLong(2, product.getPrice());
            statement.setBoolean(3, product.getStock());
            statement.setLong(4, webshopOrderId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                product.setWebshopOrder(getById(webshopOrderId));
                return getProductsByWebshopOrderId(webshopOrderId);
            }

        } catch (SQLException e) {
            throw new RepositoryException("Failed to add product to order ID " + webshopOrderId, e);
        }

        return List.of();
    }


    @Override
    public void removeProductFromOrder(Long webshopOrderId, Long productId)
            throws RepositoryException, IdNotFoundException {
        String query = "DELETE FROM products WHERE id = ? AND order_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, productId);
            statement.setLong(2, webshopOrderId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                logger.error("Product with ID {} not found in order with ID {}", productId, webshopOrderId);
                throw new ProductIdNotFoundException("Product with ID " + productId
                        + " not found in order with ID " + webshopOrderId);
            }

        } catch (SQLException e) {
            throw new RepositoryException("Failed to remove product from order ID " + webshopOrderId, e);
        }
    }


}

