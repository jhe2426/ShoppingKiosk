package com.shopping.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shopping.task.entity.CouponEntity;
import com.shopping.task.entity.UserCouponEntity;
import com.shopping.task.entity.UserEntity;



public interface UserCouponRepository extends JpaRepository<UserCouponEntity, Integer> {
    public UserCouponEntity findByUserEntityAndCouponEntity(UserEntity userEntity, CouponEntity couponEntity);
    public List<UserCouponEntity> findByCouponEntity(CouponEntity couponEntity);
    public List<UserCouponEntity> findByUserEntity(UserEntity userEntity);
}
