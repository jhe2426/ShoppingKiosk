package com.shopping.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "OrdersProduct")
@Table(name = "OrdersProduct")
public class OrdersProductEntity {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_product_number")
    private int ordersProductNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_number", nullable = false)
    private OrdersEntity ordersEntity;

    @Column(name = "product_number", nullable = false)
    private int productNumber;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    public OrdersProductEntity(OrdersEntity ordersEntity, ProductEntity productEntity, int productQuantity) {
        this.ordersEntity = ordersEntity;
        this.productNumber = productEntity.getProductNumber();
        this.productQuantity = productQuantity;
    }
}
