package edu.bbte.idde.dkim2226.spring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WSOrderInDTO {
    @NotEmpty(message = "Customer name is required")
    @Pattern(regexp = "[A-Z][a-zA-Z0-9,.:;\\-'()\\s]+", message = "Invalid customer name")
    private String customerName;

    @NotEmpty(message = "Address is required")
    @Pattern(regexp = "[a-zA-Z0-9,.:;\\-'()\\s]+", message = "Invalid address")
    private String address;

    @NotNull(message = "Order date is required")
    @Positive(message = "Amount paid must be positive")
    private Long amountPaid;

    @NotEmpty(message = "Status is required")
    @Pattern(regexp = "pending|canceled|delivered", message = "Status must be 'pending', 'canceled', or 'delivered'")
    private String status;
}
