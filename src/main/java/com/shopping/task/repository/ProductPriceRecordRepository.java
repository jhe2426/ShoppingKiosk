package com.shopping.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopping.task.entity.ProductEntity;
import com.shopping.task.entity.ProductPriceRecordEntity;

public interface ProductPriceRecordRepository extends JpaRepository<ProductPriceRecordEntity, Integer> {
    public List<ProductPriceRecordEntity> findByProductEntityOrderByModifyPriceDesc(ProductEntity productEntity);
    public List<ProductPriceRecordEntity> findByProductEntity(ProductEntity productEntity);

    @Query (
        value = 
        "SELECT " +
        "max(modify_date) AS maxModifyDate " +
        "FROM ProductPriceRecord " +
        "WHERE product_number = ?",
        nativeQuery = true
    )
    public String getMaxModifyDate(ProductEntity productEntity);
    
}
