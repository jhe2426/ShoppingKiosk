package com.shopping.task.dto.response;


import java.util.ArrayList;
import java.util.List;

import com.shopping.task.entity.ProductEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetProductListResponseDto extends ResponseDto {

    private List<Product> productList;
    
    public GetProductListResponseDto(List<ProductEntity> productEntities) {

        super("SU", "Success");

        List<Product> productList = new ArrayList<>();

        for(ProductEntity productEntity: productEntities) {
            Product product = new Product(productEntity);
            productList.add(product);
        }

        this.productList = productList;
    }
}

@Data
@NoArgsConstructor
class Product {
    private int productNumber;
    private String productName;
    private int productPrice;
    private int productDeliveryCharge;
    private String createDate;

    public Product(ProductEntity productEntity) {
        this.productNumber = productEntity.getProductNumber();
        this.productName = productEntity.getName();
        this.productPrice = productEntity.getPrice();
        this.productDeliveryCharge = productEntity.getDeliveryCharge();
        this.createDate = productEntity.getCreateDate();
    }
}
