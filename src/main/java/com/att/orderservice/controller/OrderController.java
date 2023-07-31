package com.att.orderservice.controller;

import com.att.orderservice.dto.OrderDto;
import com.att.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService OrderService;

    @Autowired
    public OrderController(OrderService OrderService) {
        this.OrderService = OrderService;
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return OrderService.getAllOrders();
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto OrderDto) {
        return OrderService.createOrder(OrderDto);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return OrderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable Long id, @RequestBody OrderDto OrderDto) throws ChangeSetPersister.NotFoundException {
        return OrderService.updateOrder(id, OrderDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        OrderService.deleteOrder(id);
    }
}
