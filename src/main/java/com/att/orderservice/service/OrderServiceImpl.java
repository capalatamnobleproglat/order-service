package com.att.orderservice.service;

import com.att.orderservice.client.ProductClient;
import com.att.orderservice.client.UserClient;
import com.att.orderservice.dto.ProductDto;
import com.att.orderservice.dto.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    private final UserClient userClient;
    private final ProductClient productClient;

    public OrderServiceImpl(OrderRepository orderRepository, UserClient userClient, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "user-service", fallbackMethod = "fallbackForGetUser")
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        // Verify that user and product exist
        UserDto userDto = userClient.getUser(orderDto.getUserId());
        ProductDto productDto = productClient.getProduct(orderDto.getProductId());

        // If user and product do not exist, throw an exception
        if(userDto == null || productDto == null) {
            throw new RuntimeException("User or product does not exist.");
        }

        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setProductId(orderDto.getProductId());
        order.setQuantity(orderDto.getQuantity());
        order.setOrderDate(orderDto.getOrderDate());

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

    private List<OrderDto> fallbackForGetUser(Throwable e) {
        e.printStackTrace();

        return List.of();
    }
}
