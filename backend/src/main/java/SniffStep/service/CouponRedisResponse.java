package SniffStep.service;

import SniffStep.entity.Coupon;

import java.time.LocalDate;

public record CouponRedisResponse(Long couponId, Integer discount, String name, String description, LocalDate endAt,
                                  Integer minOrderPrice) {

    public static CouponRedisResponse from(Coupon coupon) {
        return new CouponRedisResponse(
                coupon.getId(),
                coupon.getDiscount(),
                coupon.getName(),
                coupon.getDescription(),
                coupon.getEndAt(),
                coupon.getMinOrderPrice()
        );
    }
}

