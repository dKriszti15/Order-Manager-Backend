package edu.bbte.idde.dkim2226.desktop.view;

import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.OrderService;
import edu.bbte.idde.dkim2226.service.OrderValidationService;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame {

    public UserInterface() {
        super();
        WebshopOrder[] webshopOrders;

        OrderService orderValidationService = new OrderValidationService();
        try {
            webshopOrders = orderValidationService.findAllOrders().toArray(new WebshopOrder[0]);
        } catch (RepositoryException e) {
            webshopOrders = new WebshopOrder[0];
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }

        OrderListingPanel orderListingPanel = new OrderListingPanel(webshopOrders);
        orderListingPanel.displayOrders();

        JScrollPane scrollPane = orderListingPanel.createScrollPane();
        this.add(scrollPane, BorderLayout.CENTER);

        ButtonPanel buttonPanel = new ButtonPanel(orderValidationService, orderListingPanel);
        this.add(buttonPanel, BorderLayout.NORTH);

        this.setTitle("WebshopOrderUI");
        this.setResizable(false);
        this.setSize(new Dimension(1600, 600));
        this.setVisible(true);
    }
}
