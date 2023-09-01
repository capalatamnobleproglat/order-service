package com.att.orderservice.controller;

import com.att.orderservice.client.ProductClient;
import com.att.orderservice.client.UserClient;
import com.att.orderservice.dto.OrderDto;
import com.att.orderservice.dto.ProductDto;
import com.att.orderservice.dto.UserDto;
import com.att.orderservice.model.Order;
import com.att.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService OrderService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private ProductClient productClient;

    @Autowired
    public OrderController(OrderService OrderService) {
        this.OrderService = OrderService;
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return OrderService.getAllOrders();
    }

    @CircuitBreaker(name="createOrderCB", fallbackMethod="fallBackCreateOrder")
    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto OrderDto) {
        UserDto userDto = userClient.getUser( OrderDto.getUser_id() );
        ProductDto productDto = productClient.getProduct( OrderDto.getProduct_id() );

        if( userDto == null || productDto == null ){
            throw new RuntimeException("User or product does not exist.");
        }

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

    public OrderDto fallBackCreateOrder(OrderDto orderDto, Exception e){
        OrderDto defaultOrder = new OrderDto();
        defaultOrder.setId( -1L );
        defaultOrder.setUser_id( orderDto.getUser_id() );
        defaultOrder.setProduct_id(orderDto.getProduct_id());
        defaultOrder.setQuantity(0L);
        defaultOrder.setOrder_date( new Date() );
        return defaultOrder;
    }

}
