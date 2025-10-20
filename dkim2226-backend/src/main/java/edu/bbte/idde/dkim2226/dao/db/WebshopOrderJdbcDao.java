package edu.bbte.idde.dkim2226.dao.db;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.dkim2226.dao.WebshopOrderDao;
import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.utils.ConfigurationFactory;
import edu.bbte.idde.dkim2226.utils.MainConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class WebshopOrderJdbcDao implements WebshopOrderDao {
    private final HikariDataSource dataSource = buildDataSource();
    private static final Logger logger = LoggerFactory.getLogger(WebshopOrderJdbcDao.class);
    private static final MainConfiguration config = ConfigurationFactory.getMainConfiguration();

    public static HikariDataSource buildDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(config.getJdbcConfiguration().getDriverClass());
        dataSource.setJdbcUrl(config.getJdbcConfiguration().getUrl());
        dataSource.setUsername(config.getJdbcConfiguration().getUsername());
        dataSource.setPassword(config.getJdbcConfiguration().getPassword());
        dataSource.setMaximumPoolSize(config.getJdbcConfiguration().getPoolSize());
        return dataSource;
    }

    @Override
    public Collection<WebshopOrder> findAll() throws RepositoryException {
        Collection<WebshopOrder> orders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM orders_noautoincid")) {

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
            WebshopOrder order = new WebshopOrder(resultSet.getLong("id"),
                    resultSet.getDate("orderDate"), resultSet.getString("address"),
                    resultSet.getString("customerName"), resultSet.getLong("amountPaid"),
                    resultSet.getString("status"));
            orders.add(order);
        }
    }


    @Override
    public void create(WebshopOrder order) throws RepositoryException {
        String query = "INSERT INTO orders_noautoincid (id, orderDate, address,"
                + " customerName, amountPaid, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, order.getId());
            statement.setDate(2, new Date(order.getOrderDate().getTime()));
            statement.setString(3, order.getAddress());
            statement.setString(4, order.getCustomerName());
            statement.setLong(5, order.getAmountPaid());
            statement.setString(6, order.getStatus());
            statement.executeUpdate();
            logger.info("Order created");
        } catch (SQLException e) {
            logger.warn("Entity with id {} already exists", order.getId());
            throw new RepositoryException("Order with ID " + order.getId() + " already exists.", e);
        }
    }

    @Override
    public void update(WebshopOrder order) throws RepositoryException {
        String query = "UPDATE orders_noautoincid SET orderDate = ?, address = ?,"
                + " customerName = ?, amountPaid = ?, status = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, new Date(order.getOrderDate().getTime()));
            statement.setString(2, order.getAddress());
            statement.setString(3, order.getCustomerName());
            statement.setLong(4, order.getAmountPaid());
            statement.setString(5, order.getStatus());
            statement.setLong(6, order.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                logger.error("Order with ID {} not found.", order.getId());
                throw new RepositoryException("Order with ID " + order.getId() + " not found.");
            }
            logger.info("Order with ID {} updated.", order.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Failed to update order with ID " + order.getId(), e);
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        String query = "DELETE FROM orders_noautoincid WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                logger.error("Order with orderID {} not found.", id);
                throw new RepositoryException("Order with ID " + id + " not found.");
            }
            logger.info("Order with ID {} deleted succesfully.", id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Failed to delete order with ID " + id, e);
        }
    }

    @Override
    public WebshopOrder findById(Long id) throws RepositoryException {
        WebshopOrder foundOrder = null;
        String query = "SELECT * FROM orders_noautoincid WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                foundOrder = new WebshopOrder(resultSet.getLong("id"),
                        resultSet.getDate("orderDate"), resultSet.getString("address"),
                        resultSet.getString("customerName"), resultSet.getLong("amountPaid"),
                        resultSet.getString("status"));
            }

            if (foundOrder != null) {
                logger.info("Order with ID {} found.", id);
            } else {
                logger.error("Order with ID {} could not be found.", id);
                throw new RepositoryException("Order with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Failed to get order with ID " + id, e);
        }
        return foundOrder;
    }


}

