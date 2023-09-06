package com.shopping.task.dto.response;

import com.shopping.task.entity.ProductEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetProductResponseDto extends ResponseDto{
    private int productNumber;
    private String productName;
    private int productPrice;
    private int productDeliveryCharge;
    private String createDate;

    public GetProductResponseDto(ProductEntity productEntity) {
        super("SU", "Success");
        
        this.productNumber = productEntity.getProductNumber();
        this.productName = productEntity.getName();
        this.productPrice = productEntity.getPrice();
        this.productDeliveryCharge = productEntity.getDeliveryCharge();
        this.createDate = productEntity.getCreateDate();
    }
}
