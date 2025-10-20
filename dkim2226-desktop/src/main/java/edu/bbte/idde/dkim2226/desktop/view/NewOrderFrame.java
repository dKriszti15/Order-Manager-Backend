package edu.bbte.idde.dkim2226.desktop.view;

import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.service.OrderService;
import edu.bbte.idde.dkim2226.service.exceptions.InvalidOrderException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdAlreadyExists;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class NewOrderFrame extends JFrame {
    private final JTextField idTextField;
    private final JTextField cnameTextField;
    private final JTextField addressTextField;
    private final JTextField statusTextField;
    private final JTextField amountTextField;

    public NewOrderFrame(OrderService orderValidationService, OrderListingPanel orderListingPanel) {
        super();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(6, 2));

        idTextField = new JTextField();
        contentPane.add(new JLabel("ID:"));
        contentPane.add(idTextField);

        cnameTextField = new JTextField();
        contentPane.add(new JLabel("Customer Name: "));
        contentPane.add(cnameTextField);

        addressTextField = new JTextField();
        contentPane.add(new JLabel("Address: "));
        contentPane.add(addressTextField);

        statusTextField = new JTextField();
        contentPane.add(new JLabel("Status: "));
        contentPane.add(statusTextField);

        amountTextField = new JTextField();
        contentPane.add(new JLabel("Amount paid: "));
        contentPane.add(amountTextField);

        JButton cancelButton = new JButton("Cancel");
        contentPane.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton button = new JButton("Submit");
        contentPane.add(button);

        button.addActionListener(e -> {
            try {
                long amountPaid = Long.parseLong(amountTextField.getText());

                WebshopOrder newOrder = new WebshopOrder(
                        Long.parseLong(idTextField.getText()),
                        new Date(),
                        addressTextField.getText(),
                        cnameTextField.getText(),
                        amountPaid,
                        statusTextField.getText()
                );
                orderValidationService.addOrder(newOrder);

                WebshopOrder[] updatedOrders = orderValidationService.findAllOrders().toArray(new WebshopOrder[0]);

                orderListingPanel.refresh(updatedOrders);

                idTextField.setText("");
                addressTextField.setText("");
                cnameTextField.setText("");
                amountTextField.setText("");
                statusTextField.setText("");
                JOptionPane.showMessageDialog(null, "Order added successfully!");

            } catch (InvalidOrderException | OrderIdAlreadyExists | RepositoryException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        });

        this.add(contentPane);
        this.setTitle("Add new order");
        this.setSize(new Dimension(450, 300));
        this.setVisible(true);
    }
}
