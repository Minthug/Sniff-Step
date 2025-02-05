package SniffStep.service.request;

public record RegisterUserCouponRequest(Long memberId, Long couponId) {

    public static RegisterUserCouponRequest of(Long memberId, Long couponId) {
        return new RegisterUserCouponRequest(memberId, couponId);
    }
}
