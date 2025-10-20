package edu.bbte.idde.dkim2226.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSOrderOutDTO {
    private Long id;

    private String orderDate;

    private String customerName;

    private String address;

    private Long amountPaid;

    private String status;

}
