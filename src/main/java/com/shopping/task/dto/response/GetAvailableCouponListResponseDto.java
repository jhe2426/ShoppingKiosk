package com.shopping.task.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.shopping.task.entity.CouponEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAvailableCouponListResponseDto extends ResponseDto{
    private List<CouponSummary> couponList;

    public GetAvailableCouponListResponseDto(List<CouponEntity> couponEntities) {
        
        super("SU", "Success");

        List<CouponSummary> couponList = new ArrayList<>();

        for(CouponEntity couponEntity: couponEntities) {
            CouponSummary couponSummary = new CouponSummary(couponEntity);
            couponList.add(couponSummary);
        }

        this.couponList = couponList;
    }
} 

@Data
@NoArgsConstructor
class CouponSummary {
    private int couponNumber;
    private String couponName;
    private int applicationPrice;
    private int availableProductNumber;

    public CouponSummary(CouponEntity couponEntity) {
        this.couponNumber = couponEntity.getCouponNumber();
        this.couponName = couponEntity.getName();
        this.applicationPrice = couponEntity.getApplicationPrice();
        this.availableProductNumber = couponEntity.getCoverage();
    }
}
