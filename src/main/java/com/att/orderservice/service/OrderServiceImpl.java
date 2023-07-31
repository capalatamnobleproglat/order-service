package com.att.orderservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.att.orderservice.model.Order;
import com.att.orderservice.dto.OrderDto;
import com.att.orderservice.mapper.OrderDtoMapper;
import com.att.orderservice.mapper.OrderMapper;
import com.att.orderservice.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository OrderRepository;

    private final OrderMapper OrderMapper;

    private final OrderDtoMapper OrderDtoMapper;
    
    public OrderServiceImpl(OrderRepository OrderRepository, OrderMapper OrderMapper, OrderDtoMapper OrderDtoMapper) {
        this.OrderRepository = OrderRepository;
        this.OrderDtoMapper = OrderDtoMapper;
        this.OrderMapper = OrderMapper;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> Orders = OrderRepository.findAll();
        return Orders.stream()
                .map(OrderMapper::Order2OrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto createOrder(OrderDto OrderDto) {
        Order Order = OrderDtoMapper.OrderDTO2Order(OrderDto);
        Order = OrderRepository.save(Order);
        return OrderMapper.Order2OrderDTO(Order);
    }

    @Override
    public OrderDto getOrderById(Long id) throws ChangeSetPersister.NotFoundException {
        Order Order = OrderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return OrderMapper.Order2OrderDTO(Order);
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto OrderDto) throws ChangeSetPersister.NotFoundException {
        Order existingOrder = OrderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        BeanUtils.copyProperties(OrderDto, existingOrder, "id");

        existingOrder = OrderRepository.save(existingOrder);
        return OrderMapper.Order2OrderDTO(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) throws ChangeSetPersister.NotFoundException {
        Order Order = OrderRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        OrderRepository.delete(Order);
    }

}
