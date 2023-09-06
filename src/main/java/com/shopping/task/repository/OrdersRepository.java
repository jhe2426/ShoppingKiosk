package com.shopping.task.repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopping.task.entity.OrdersEntity;
import com.shopping.task.entity.UserEntity;


public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer> {
    public OrdersEntity findByOrdersNumber(int ordersNumber);
    
    @Query (
        value = 
        "SELECT " +
        "orders_number " +
        "FROM Orders " +
        "ORDER BY order_date DESC " +
        "LIMIT 1",
        nativeQuery = true
    )
    public int getRecentOrdersNumber(UserEntity userEntity);

    public List<OrdersEntity> findByUserEntity(UserEntity userEntity);
}
