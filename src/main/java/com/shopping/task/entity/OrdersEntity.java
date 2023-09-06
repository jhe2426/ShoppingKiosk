package com.shopping.task.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Orders")
@Table(name = "Orders")
public class OrdersEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "orders_number")
    private int ordersNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_number", nullable = false)
    private UserEntity userEntity;

    @Column (name = "coupon_pk_number")
    private int couponPkNumber;

    @Column (name = "total_order_count", nullable = false)
    private int totalOrderCount;

    @Column (name = "order_price", nullable = false)
    private int orderPrice;

    @Column (name = "delivery_charge", nullable = true)
    @ColumnDefault("0")
    private int deliveryCharge;

    @Column (name = "order_date", nullable = false, length = 19)
    private String orderDate;

    public OrdersEntity(UserEntity userEntity, int totalProductCount, int totalProductPrice, int deliveryCharge) {
        Date now = new Date(); 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderDate = simpleDateFormat.format(now);

        this.userEntity = userEntity;
        this.totalOrderCount = totalProductCount;
        this.orderPrice = totalProductPrice;
        this.deliveryCharge = deliveryCharge;
        this.orderDate = orderDate;
    }

    public OrdersEntity(UserEntity userEntity, int totalProductCount, int totalProductPrice, int couponPkNumber, int deliveryCharge) {
        Date now = new Date(); 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderDate = simpleDateFormat.format(now);

        this.userEntity = userEntity;
        this.couponPkNumber = couponPkNumber;
        this.totalOrderCount = totalProductCount;
        this.orderPrice = totalProductPrice;
        this.deliveryCharge = deliveryCharge;
        this.orderDate = orderDate;
    }

}
