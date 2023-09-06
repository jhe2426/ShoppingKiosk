package com.shopping.task.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.shopping.task.dto.request.PatchAllProductRequstDto;
import com.shopping.task.dto.request.PatchPriceProductRequestDto;
import com.shopping.task.dto.request.PostProductRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Product")
@Table(name = "Product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "product_number")
    private int productNumber;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column (name = "delivery_charge", nullable = true)
    @ColumnDefault("0")
    private int deliveryCharge;

    @Column (name = "create_date", nullable = false, length = 19)
    private String createDate;

    public ProductEntity(PostProductRequestDto dto) {
        Date now = new Date(); 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDate = simpleDateFormat.format(now);

        this.name = dto.getName();
        this.price = dto.getPrice();
        this.deliveryCharge = dto.getDeliveryCharge(); 
        this.createDate = createDate;
    }

    public ProductEntity(ProductEntity productEntity, PatchAllProductRequstDto dto) {
        this.productNumber = dto.getProductNumber();
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.deliveryCharge = dto.getDeliveryCharge();
        this.createDate = productEntity.getCreateDate();
    }

    public ProductEntity(ProductEntity productEntity, PatchPriceProductRequestDto dto) {
        this.productNumber = dto.getProductNumber();
        this.name = productEntity.getName();
        this.price = dto.getPrice();
        this.deliveryCharge = productEntity.getDeliveryCharge();
        this.createDate = productEntity.getCreateDate();
    }
}
