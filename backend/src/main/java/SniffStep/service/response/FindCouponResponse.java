package SniffStep.service.response;

import SniffStep.entity.Coupon;

import java.time.LocalDate;

public record FindCouponResponse(Long couponId, String name, String description, Integer discount, LocalDate endAt) {
    public static FindCouponResponse from(Coupon coupon) {
        return new FindCouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDescription(),
                coupon.getDiscount(),
                coupon.getEndAt()
        );
    }
}
