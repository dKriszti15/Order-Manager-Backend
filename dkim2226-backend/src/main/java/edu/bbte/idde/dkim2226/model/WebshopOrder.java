package edu.bbte.idde.dkim2226.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class WebshopOrder extends BaseEntity {

    private Date orderDate;
    private String customerName;
    private String address;
    private Long amountPaid;
    private String status;

    public WebshopOrder(Long id, Date orderDate, String address, String customerName, Long amountPaid, String status) {
        super(id);
        this.orderDate = new Date(orderDate.getTime());
        this.address = address;
        this.customerName = customerName;
        this.amountPaid = amountPaid;
        this.status = status;
    }
}
