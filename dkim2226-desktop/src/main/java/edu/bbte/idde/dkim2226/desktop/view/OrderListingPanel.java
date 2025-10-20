package edu.bbte.idde.dkim2226.desktop.view;

import edu.bbte.idde.dkim2226.model.WebshopOrder;

import javax.swing.*;
import java.awt.*;

public class OrderListingPanel extends JPanel {
    private WebshopOrder[] webshopOrders;

    public OrderListingPanel(WebshopOrder... webshopOrders) {
        super();
        this.webshopOrders = webshopOrders != null ? webshopOrders.clone() : new WebshopOrder[0];
        this.setLayout(new GridLayout(0, 6));
    }

    public void displayOrders() {
        this.removeAll();

        this.add(new JLabel("Order ID"));
        this.add(new JLabel("Customer Name"));
        this.add(new JLabel("Order Address"));
        this.add(new JLabel("Order Amount Paid"));
        this.add(new JLabel("Order Date"));
        this.add(new JLabel("Order Status"));

        for (WebshopOrder webshopOrder : webshopOrders) {
            this.add(new JLabel(String.valueOf(webshopOrder.getId())));
            this.add(new JLabel(webshopOrder.getCustomerName()));
            this.add(new JLabel(webshopOrder.getAddress()));
            this.add(new JLabel(String.valueOf(webshopOrder.getAmountPaid())));
            this.add(new JLabel(String.valueOf(webshopOrder.getOrderDate())));
            this.add(new JLabel(webshopOrder.getStatus()));
        }

        this.revalidate();
        this.repaint();
    }

    public void refresh(WebshopOrder... webshopOrders) {
        this.webshopOrders = webshopOrders != null ? webshopOrders.clone() : new WebshopOrder[0];
        displayOrders();
    }

    public JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane(this, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
}
