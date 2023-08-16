package com.att.orderservice.service;

import com.att.orderservice.dto.OrderDto;
import org.springframework.data.crossstore.ChangeSetPersister;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();

    OrderDto getOrderById(Long id) throws ChangeSetPersister.NotFoundException;

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrder(Long id, OrderDto orderDto) throws ChangeSetPersister.NotFoundException;

    void deleteOrder(Long id) throws ChangeSetPersister.NotFoundException;
}
