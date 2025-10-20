package edu.bbte.idde.dkim2226.desktop.view;

import edu.bbte.idde.dkim2226.service.OrderService;

import javax.swing.*;

public class ButtonPanel extends JPanel {

    public ButtonPanel(OrderService orderValidationService, OrderListingPanel orderListingPanel) {
        super();

        CustomButton addButton = new CustomButton("Add");
        CustomButton deleteButton = new CustomButton("Delete");
        CustomButton updateButton = new CustomButton("Update");

        addButton.addActionListener(e -> {
            new NewOrderFrame(orderValidationService, orderListingPanel);
        });

        deleteButton.addActionListener(e -> {
            DeleteOrderFrame deleteOrderFrame = new DeleteOrderFrame(orderValidationService, orderListingPanel);
            deleteOrderFrame.showFrame();
        });

        updateButton.addActionListener(e -> {
            new UpdateOrderFrame(orderValidationService, orderListingPanel);
        });


        this.add(addButton);
        this.add(deleteButton);
        this.add(updateButton);
    }
}
