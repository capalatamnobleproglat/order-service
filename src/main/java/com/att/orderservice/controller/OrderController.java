package com.att.orderservice.controller;

import com.att.orderservice.dto.OrderDto;
import com.att.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Value("${example.property}")
    private String exampleProperty;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @CircuitBreaker(name = "createOrderCB", fallbackMethod = "fallBackCreateOrder")
    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDto) { return orderService.createOrder(orderDto); }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        logger.info("exampleProperty: {}", exampleProperty);
        // int errorOperation = 0/0;

        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) throws ChangeSetPersister.NotFoundException {
        return orderService.updateOrder(id, orderDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        orderService.deleteOrder(id);
    }

    public OrderDto fallBackCreateOrder(OrderDto orderDto, Exception e) {
        OrderDto defaultOrder = new OrderDto();
        defaultOrder.setId(-1L);
        defaultOrder.setUserId(orderDto.getUserId());
        defaultOrder.setProductId(-1L);
        defaultOrder.setQuantity(0);
        defaultOrder.setOrderDate(new Date());

        return defaultOrder;
    }
}
