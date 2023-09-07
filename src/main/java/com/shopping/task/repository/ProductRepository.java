package com.shopping.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopping.task.entity.ProductEntity;


public interface ProductRepository extends JpaRepository<ProductEntity, Integer>  {
    public ProductEntity findByProductNumber(int productNumber);
    public ProductEntity findByNameAndPrice(String name, int price);

    
    @Query (
        value = 
        "SELECT * " +
        "FROM Product " +
        "ORDER BY create_date DESC",
        nativeQuery = true
    )
    public List<ProductEntity> getProductList();
}
