package com.att.orderservice.mapper;

import com.att.orderservice.dto.OrderDto;
import com.att.orderservice.model.Order;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-31T20:36:57+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class OrderDtoMapperImpl implements OrderDtoMapper {

    @Override
    public Order OrderDTO2Order(OrderDto OrderDTO) {
        if ( OrderDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setId( OrderDTO.getId() );
        order.setOrderDate( OrderDTO.getOrderDate() );
        order.setProductId( OrderDTO.getProductId() );
        order.setQuantity( OrderDTO.getQuantity() );
        order.setUserId( OrderDTO.getUserId() );

        return order;
    }
}
