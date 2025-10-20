package edu.bbte.idde.dkim2226.service.utils;

import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.exceptions.InvalidOrderException;

public class ValidateOrderServiceAttributes {
    private static final String VALID_CHARACTERS = "[a-zA-Z0-9,.!?:;\\-'()\\s]+";

    public void isValid(WebshopOrder webshopOrder) throws InvalidOrderException {
        validateOrderDate(webshopOrder.getOrderDate().toString());
        validateCustomerName(webshopOrder.getCustomerName());
        validateAddress(webshopOrder.getAddress());
        validateAmountPaid(webshopOrder.getAmountPaid());
        validateStatus(webshopOrder.getStatus());
    }

    private void validateOrderDate(String orderDate) throws InvalidOrderException {
        if (!orderDate.matches(VALID_CHARACTERS)) {
            throw new InvalidOrderException("Invalid order date: " + orderDate);
        }
    }

    private void validateCustomerName(String customerName) throws InvalidOrderException {
        if (customerName == null || !customerName.matches(VALID_CHARACTERS)) {
            throw new InvalidOrderException("Invalid customer name: " + customerName);
        }
    }

    private void validateAddress(String address) throws InvalidOrderException {
        if (address == null || !address.matches(VALID_CHARACTERS)) {
            throw new InvalidOrderException("Invalid address: " + address);
        }
    }

    private void validateAmountPaid(long amountPaid) throws InvalidOrderException {
        if (amountPaid < 0) {
            throw new InvalidOrderException("Invalid amount paid: " + amountPaid);
        }
    }

    private void validateStatus(String status) throws InvalidOrderException {
        if (!"canceled".equals(status) && !"delivered".equals(status) && !"pending".equals(status)) {
            throw new InvalidOrderException("Invalid status: " + status);
        }
    }
}
