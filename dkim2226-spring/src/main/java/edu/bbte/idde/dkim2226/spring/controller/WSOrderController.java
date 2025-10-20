package edu.bbte.idde.dkim2226.spring.controller;

import edu.bbte.idde.dkim2226.spring.dao.exceptions.IdAlreadyExistsException;
import edu.bbte.idde.dkim2226.spring.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.spring.dto.WSOrderInDTO;
import edu.bbte.idde.dkim2226.spring.dto.WSOrderOutDTO;
import edu.bbte.idde.dkim2226.spring.service.OrderService;
import edu.bbte.idde.dkim2226.spring.service.exceptions.InvalidOrderException;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdAlreadyExists;
import edu.bbte.idde.dkim2226.spring.service.exceptions.OrderIdNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/webshopOrders")
@CrossOrigin(origins = "http://localhost:5173")
public class WSOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public Collection<WSOrderOutDTO> findAllOrders(@RequestParam(value = "customerName",
            required = false) String customerName)
            throws RepositoryException {
        if (customerName != null) {
            return orderService.findOrdersByCustomerName(customerName);
        }
        return orderService.findAllOrders();
    }

    @GetMapping("/{customerName}/orders")
    public Collection<WSOrderOutDTO> findOrdersByCustomerName(@PathVariable String customerName)
            throws RepositoryException {
        return orderService.findOrdersByCustomerName(customerName);
    }

    @GetMapping("/{id}")
    public WSOrderOutDTO findById(@PathVariable Long id)
            throws OrderIdNotFoundException, RepositoryException {
        return orderService.findOrderById(id);
    }

    @PostMapping
    public Collection<WSOrderOutDTO> addOrder(@RequestBody @Valid WSOrderInDTO order)
            throws InvalidOrderException, OrderIdAlreadyExists, RepositoryException {
        return orderService.addOrder(order);
    }

    @DeleteMapping("/{id}")
    public Collection<WSOrderOutDTO> removeOrder(@PathVariable Long id)
            throws OrderIdNotFoundException, RepositoryException {
        return orderService.removeOrder(id);
    }

    @PutMapping("/{id}")
    public WSOrderOutDTO updateOrder(@PathVariable Long id, @RequestBody @Valid WSOrderInDTO order)
            throws InvalidOrderException, RepositoryException, IdAlreadyExistsException {
        return orderService.updateOrder(id, order);
    }

}
