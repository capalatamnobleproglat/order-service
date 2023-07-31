package com.att.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private Long userId;
    private Long productId;
    private Long quantity;
    private Date orderDate;

}
