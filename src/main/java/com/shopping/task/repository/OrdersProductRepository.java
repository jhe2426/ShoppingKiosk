package com.shopping.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.task.entity.OrdersEntity;
import com.shopping.task.entity.OrdersProductEntity;

public interface OrdersProductRepository extends JpaRepository<OrdersProductEntity, Integer> {
    public List<OrdersProductEntity> findByOrdersEntity(OrdersEntity ordersEntity);
}
