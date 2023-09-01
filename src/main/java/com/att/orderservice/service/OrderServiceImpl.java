package com.att.orderservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import com.att.orderservice.repository.OrderRepository;
import java.util.List;
import com.att.orderservice.dto.OrderDto;
import com.att.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository OrderRepository;

    public OrderServiceImpl(OrderRepository OrderRepository) {
        this.OrderRepository = OrderRepository;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> Orders = OrderRepository.findAll();
        return Orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto createOrder(OrderDto OrderDto) {
        Order Order = convertToEntity(OrderDto);
        Order = OrderRepository.save(Order);
        return convertToDto(Order);
    }

    @Override
    public OrderDto getOrderById(Long id) throws ChangeSetPersister.NotFoundException {
        Order Order = OrderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return convertToDto(Order);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto OrderDto) throws ChangeSetPersister.NotFoundException {
        Order existingOrder = OrderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos del Ordero existente con la información del DTO
        BeanUtils.copyProperties(OrderDto, existingOrder, "id");

        existingOrder = OrderRepository.save(existingOrder);
        return convertToDto(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) throws ChangeSetPersister.NotFoundException {
        Order Order = OrderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        OrderRepository.delete(Order);
    }

    private OrderDto convertToDto(Order Order) {
        OrderDto OrderDto = new OrderDto();
        BeanUtils.copyProperties(Order, OrderDto);
        return OrderDto;
    }

    private Order convertToEntity(OrderDto OrderDto) {
        Order Order = new Order();
        BeanUtils.copyProperties(OrderDto, Order);
        return Order;
    }
}
