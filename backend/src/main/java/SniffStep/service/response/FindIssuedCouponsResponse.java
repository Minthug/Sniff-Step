package SniffStep.service.response;

import SniffStep.entity.UserCoupon;

import java.util.List;
import java.util.stream.Collectors;

public record FindIssuedCouponsResponse(List<FindIssuedCouponResponse> issuedCoupons) {
    public static FindIssuedCouponsResponse from(List<UserCoupon> issuedCoupons) {
        return issuedCoupons.stream()
                .map(UserCoupon::getCoupon)
                .map(FindIssuedCouponResponse::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FindIssuedCouponsResponse::new));
    }
}
