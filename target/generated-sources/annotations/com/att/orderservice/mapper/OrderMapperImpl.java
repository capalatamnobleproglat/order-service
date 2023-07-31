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
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto Order2OrderDTO(Order Order) {
        if ( Order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setId( Order.getId() );
        orderDto.setOrderDate( Order.getOrderDate() );
        orderDto.setProductId( Order.getProductId() );
        orderDto.setQuantity( Order.getQuantity() );
        orderDto.setUserId( Order.getUserId() );

        return orderDto;
    }
}
