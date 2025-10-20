package edu.bbte.idde.dkim2226.web.servlet;

public class WebshopOrderDTO {
    private Long id;
    private String customerName;
    private String address;
    private Long amountPaid;
    private String status;

    public WebshopOrderDTO() {
        super();
    }

    public WebshopOrderDTO(Long id, String customerName, String address, Long amountPaid, String status) {
        super();
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.amountPaid = amountPaid;
        this.status = status;

    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WebshopOrderDTO{"
                + "id=" + id
                + ", customerName='" + customerName + '\''
                + ", address='" + address + '\''
                + ", amountPaid=" + amountPaid
                + ", status='" + status + '\'' + '}';
    }
}
