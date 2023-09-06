package com.shopping.task.dto.response;

import com.shopping.task.entity.ProductEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetParticularDateProductPriceResponseDto extends ResponseDto {
    private int price;

    public GetParticularDateProductPriceResponseDto(ProductEntity productEntity) {
        super("SU", "Success");
        this.price = productEntity.getPrice();
    }

    public GetParticularDateProductPriceResponseDto(int searchResultPrice) {
        super("SU", "Success");
        this.price = searchResultPrice;
    }
}
