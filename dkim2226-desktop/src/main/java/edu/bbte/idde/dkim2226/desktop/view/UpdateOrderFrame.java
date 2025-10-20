package edu.bbte.idde.dkim2226.desktop.view;

import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.service.OrderService;
import edu.bbte.idde.dkim2226.service.exceptions.InvalidOrderException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class UpdateOrderFrame extends JFrame {
    private final JTextField cnameTextField;
    private final JTextField addressTextField;
    private final JTextField statusTextField;
    private final JTextField amountTextField;
    private final JTextField orderToUpdateTextField;

    public UpdateOrderFrame(OrderService orderValidationService, OrderListingPanel orderListingPanel) {
        super();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 2));

        orderToUpdateTextField = new JTextField();
        mainPanel.add(new JLabel("Order ID to update:"));
        mainPanel.add(orderToUpdateTextField);

        cnameTextField = new JTextField();
        mainPanel.add(new JLabel("Customer Name: "));
        mainPanel.add(cnameTextField);

        addressTextField = new JTextField();
        mainPanel.add(new JLabel("Address: "));
        mainPanel.add(addressTextField);

        statusTextField = new JTextField();
        mainPanel.add(new JLabel("Status: "));
        mainPanel.add(statusTextField);

        amountTextField = new JTextField();
        mainPanel.add(new JLabel("Amount paid: "));
        mainPanel.add(amountTextField);

        JButton cancelButton = new JButton("Cancel");
        mainPanel.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton button = new JButton("Update");
        mainPanel.add(button);

        button.addActionListener(e -> {
            try {
                long orderID = Long.parseLong(orderToUpdateTextField.getText());

                WebshopOrder newOrder = new WebshopOrder(orderID, new Date(), addressTextField.getText(),
                        cnameTextField.getText(), Long.parseLong(amountTextField.getText()), statusTextField.getText());

                orderValidationService.updateOrder(newOrder);

                WebshopOrder[] updatedOrders = orderValidationService.findAllOrders().toArray(new WebshopOrder[0]);
                orderListingPanel.refresh(updatedOrders);

                orderToUpdateTextField.setText("");
                addressTextField.setText("");
                cnameTextField.setText("");
                amountTextField.setText("");
                statusTextField.setText("");

                JOptionPane.showMessageDialog(null, "Order updated successfully!");

            } catch (InvalidOrderException | OrderIdNotFoundException | RepositoryException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        this.add(mainPanel);

        this.setTitle("Update Order");
        this.setSize(450, 300);
        this.setVisible(true);
    }
}
