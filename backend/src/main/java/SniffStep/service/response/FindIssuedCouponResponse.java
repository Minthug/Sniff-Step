package SniffStep.service.response;

import SniffStep.entity.Coupon;

import java.time.LocalDate;

public record FindIssuedCouponResponse(Long couponId, String name,
                                       String description, Integer discount, LocalDate endAt) {

    public static FindIssuedCouponResponse from(Coupon coupon) {
        return new FindIssuedCouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDescription(),
                coupon.getDiscount(),
                coupon.getEndAt()
        );
    }
}
