package edu.bbte.idde.dkim2226.spring.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "db_products")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Product extends BaseEntity {
    private String name;
    private Long price;
    private Boolean stock;
    @ManyToOne(cascade = CascadeType.ALL)
    private WebshopOrder webshopOrder;
}
