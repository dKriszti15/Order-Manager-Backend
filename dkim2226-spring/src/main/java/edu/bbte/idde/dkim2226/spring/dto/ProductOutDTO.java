package edu.bbte.idde.dkim2226.spring.dto;

import lombok.Data;

@Data
public class ProductOutDTO {
    private Long id;
    private String name;
    private Long price;
    private Boolean stock;
}
