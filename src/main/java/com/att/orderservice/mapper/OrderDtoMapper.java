package com.att.orderservice.mapper;

import org.mapstruct.Mapper;

import com.att.orderservice.dto.OrderDto;
import com.att.orderservice.model.Order;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    Order OrderDTO2Order(OrderDto OrderDTO);

}