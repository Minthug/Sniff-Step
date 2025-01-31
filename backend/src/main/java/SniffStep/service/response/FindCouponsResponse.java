package SniffStep.service.response;

import SniffStep.entity.Coupon;

import java.util.List;
import java.util.stream.Collectors;

public record FindCouponsResponse(List<FindCouponResponse> coupons) {
    public static FindCouponsResponse from(List<Coupon> coupons) {
        return coupons.stream()
                .map(FindCouponResponse::from)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        FindCouponsResponse::new
                ));
    }
}
