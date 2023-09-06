package com.shopping.task.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.shopping.task.entity.ProductEntity;
import com.shopping.task.entity.ProductPriceRecordEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetProductPriceRecordResponseDto extends ResponseDto {

    private List<ProductPriceRecordSummary> productPriceRecordList;

    public GetProductPriceRecordResponseDto(ProductEntity productEntity, List<ProductPriceRecordEntity> productPriceRecordEntities) {

        super("SU", "Success");

        List<ProductPriceRecordSummary> productPriceRecordList = new ArrayList<>();

        for (ProductPriceRecordEntity productPriceRecordEntity: productPriceRecordEntities) {
            ProductPriceRecordSummary productPriceRecordSummary = new ProductPriceRecordSummary(productEntity, productPriceRecordEntity);
            productPriceRecordList.add(productPriceRecordSummary);
        }

        this.productPriceRecordList = productPriceRecordList;
    }
}

@Data
@NoArgsConstructor
class ProductPriceRecordSummary {
    private int productRecordNumber;
    private int prodcutNumber;
    private String modifyDate;

    public ProductPriceRecordSummary(ProductEntity productEntity, ProductPriceRecordEntity priceRecordEntity) {
        this.productRecordNumber = priceRecordEntity.getProductRecordNumber();
        this.prodcutNumber = productEntity.getProductNumber();
        this.modifyDate = priceRecordEntity.getModifyDate();
    }
}
