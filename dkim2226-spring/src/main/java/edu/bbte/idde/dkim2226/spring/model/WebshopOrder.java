package edu.bbte.idde.dkim2226.spring.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "db_webshoporders")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class WebshopOrder extends BaseEntity {
    private Date orderDate;
    private String customerName;
    private String address;
    private Long amountPaid;
    private String status;
    @OneToMany(mappedBy = "webshopOrder", cascade = CascadeType.ALL)
    private Collection<Product> products;
}
