package com.att.orderservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.att.orderservice.model.Order;
import com.att.orderservice.dto.OrderDto;
import com.att.orderservice.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = convertToEntity(orderDto);
        order = orderRepository.save(order);
        return convertToDto(order);
    }

    @Override
    public OrderDto getOrderById(Long id) throws ChangeSetPersister.NotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return convertToDto(order);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) throws ChangeSetPersister.NotFoundException {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos de la orden existente con la información del DTO
        BeanUtils.copyProperties(orderDto, existingOrder, "id");

        existingOrder = orderRepository.save(existingOrder);
        return convertToDto(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) throws ChangeSetPersister.NotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        orderRepository.delete(order);
    }

    private OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        return orderDto;
    }

    private Order convertToEntity(OrderDto orderDto) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        return order;
    }
}
