package edu.bbte.idde.dkim2226.spring.controller;

import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdAlreadyExistsException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdNotFoundException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.spring.dto.ProductInDTO;
import edu.bbte.idde.dkim2226.spring.dto.ProductOutDTO;
import edu.bbte.idde.dkim2226.spring.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/webshopOrders/{webshopOrderId}/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public Collection<ProductOutDTO> findProductsByWebshopOrderId(@PathVariable Long webshopOrderId)
            throws RepositoryException, IdNotFoundException {
        return orderService.findProductsByWebshopOrderId(webshopOrderId);
    }

    @PostMapping
    public Collection<ProductOutDTO> addProductToOrder(@PathVariable Long webshopOrderId,
                                                       @RequestBody @Valid ProductInDTO product)
            throws RepositoryException, IdNotFoundException, IdAlreadyExistsException {
        return orderService.addProductToOrder(webshopOrderId, product);
    }

    @DeleteMapping("/{productId}")
    public void removeProductFromOrder(@PathVariable Long webshopOrderId,
                                                            @PathVariable Long productId)
            throws RepositoryException, IdNotFoundException, IdAlreadyExistsException {
        orderService.removeProductFromOrder(webshopOrderId, productId);
    }
}
