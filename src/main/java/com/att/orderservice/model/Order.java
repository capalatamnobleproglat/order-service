package com.att.orderservice.model;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "product_id")
    private Long product_id;
    private Long quantity;
    @Column(name = "order_date")
    private Date orderDate;

}
