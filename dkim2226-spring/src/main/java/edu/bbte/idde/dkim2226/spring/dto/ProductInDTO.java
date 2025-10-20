package edu.bbte.idde.dkim2226.spring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductInDTO {
    @NotEmpty(message = "Product name is required")
    @Pattern(regexp = "[A-Z][a-zA-Z0-9,.:;\\-'()\\s]+", message = "Invalid product name")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Long price;

    private Boolean stock;
}
