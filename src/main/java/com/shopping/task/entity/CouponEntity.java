package com.shopping.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.shopping.task.common.constant.CouponType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Coupon")
@Table(name = "Coupon")
public class CouponEntity implements Comparable<CouponEntity> {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "coupon_number")
    private int couponNumber;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column (name = "coupon_type", nullable = false)
    private CouponType couponType;

    @Column (name = "application_price", nullable = false)
    private int applicationPrice;

    @Column(nullable = true)
    @ColumnDefault("0")
    private int coverage;


    @Override
    public int compareTo(CouponEntity o) {
        return getCouponNumber() - o.getCouponNumber();
    }
}
