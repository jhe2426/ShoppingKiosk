package com.shopping.task.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

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
@Entity(name = "ProductPriceRecord")
@Table(name = "ProductPriceRecord")
public class ProductPriceRecordEntity {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "product_record_number")
    private int productRecordNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_number", nullable = false)
    private ProductEntity productEntity;

    @Column(name = "modify_date", nullable = false, length = 19)
    private String modifyDate;

    @Column (name = "modify_price", nullable = false)
    private int modifyPrice;


    public ProductPriceRecordEntity (ProductEntity productEntity) {
        Date now = new Date(); 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String modifyDate = simpleDateFormat.format(now);

        this.productEntity = productEntity;
        this.modifyDate = modifyDate;
        this.modifyPrice = productEntity.getPrice();
    }

    public ProductPriceRecordEntity (ProductEntity productEntity, int modifyPrice) {
        Date now = new Date(); 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String modifyDate = simpleDateFormat.format(now);

        this.productEntity = productEntity;
        this.modifyDate = modifyDate;
        this.modifyPrice = modifyPrice;
    }
}
