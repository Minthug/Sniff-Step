package SniffStep.service;

import SniffStep.entity.Coupon;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;

@RedisHash(value = "coupon", timeToLive = 60 * 60)
public record CouponResponse(Long id, String name, Integer discount, LocalDate endAt) {

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscount(),
                coupon.getEndAt()
        );
    }
}
