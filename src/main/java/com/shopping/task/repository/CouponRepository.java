package com.shopping.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.task.entity.CouponEntity;
import java.util.List;


public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {
    public CouponEntity findByCouponNumber(int couponNumber);
    public List<CouponEntity> findByCoverage(int coverage);
}
