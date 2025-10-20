package edu.bbte.idde.dkim2226.desktop.view;

import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.OrderService;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteOrderFrame extends JFrame {
    private final JTextField txtOrder;

    public DeleteOrderFrame(OrderService orderValidationService, OrderListingPanel orderListingPanel) {
        super();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(2, 2));

        JLabel lblOrder = new JLabel("Order ID to delete:");
        txtOrder = new JTextField();

        contentPane.add(lblOrder);
        contentPane.add(txtOrder);

        JButton cancelBtn = new JButton("Cancel");
        JButton btnDelete = new JButton("Delete");

        contentPane.add(cancelBtn);
        contentPane.add(btnDelete);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Long orderID = Long.parseLong(txtOrder.getText());
                orderValidationService.removeOrder(orderID);
                WebshopOrder[] updatedOrders = orderValidationService.findAllOrders().toArray(new WebshopOrder[0]);
                orderListingPanel.refresh(updatedOrders);
                txtOrder.setText("");
                JOptionPane.showMessageDialog(null, "Order deleted successfully!");
            } catch (OrderIdNotFoundException | RepositoryException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        setContentPane(contentPane);
        setTitle("Delete Order");
        setSize(300, 150);
    }

    public void showFrame() {
        setVisible(true);
    }
}
