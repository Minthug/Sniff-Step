package SniffStep.service.response;

import SniffStep.entity.Coupon;
import SniffStep.entity.UserCoupon;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FindIssuedCouponsResponse {
    private final List<FindIssuedCouponResponse> coupons;

    public FindIssuedCouponsResponse(List<FindIssuedCouponResponse> coupons) {
        this.coupons = coupons;
    }

    public static FindIssuedCouponsResponse from(final List<UserCoupon> issuedCoupons) {
        return issuedCoupons.stream()
                .map(UserCoupon::getCoupon)
                .map(FindIssuedCouponResponse::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FindIssuedCouponsResponse::new));
    }

    public List<FindIssuedCouponResponse> getCoupons() {
        return coupons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindIssuedCouponsResponse that = (FindIssuedCouponsResponse) o;
        return Objects.equals(coupons, that.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coupons);
    }

    @Override
    public String toString() {
        return "FindIssuedCouponsResponse{" +
                "coupons=" + coupons +
                '}';
    }

    public static class FindIssuedCouponResponse {
        private final Long couponId;
        private final String name;
        private final String description;
        private final Integer discount;
        private final LocalDate endAt;

        public FindIssuedCouponResponse(Long couponId, String name, String description, Integer discount, LocalDate endAt) {
            this.couponId = couponId;
            this.name = name;
            this.description = description;
            this.discount = discount;
            this.endAt = endAt;
        }

        public static FindIssuedCouponResponse from(final Coupon coupon) {
            return new FindIssuedCouponResponse(
                    coupon.getId(),
                    coupon.getName(),
                    coupon.getDescription(),
                    coupon.getDiscount(),
                    coupon.getEndAt()
            );
        }

        public Long getCouponId() {
            return couponId;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Integer getDiscount() {
            return discount;
        }

        public LocalDate getEndAt() {
            return endAt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FindIssuedCouponResponse that = (FindIssuedCouponResponse) o;
            return Objects.equals(couponId, that.couponId) &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(description, that.description) &&
                    Objects.equals(discount, that.discount) &&
                    Objects.equals(endAt, that.endAt);
        }

        @Override
        public int hashCode() {
            return Objects.hash(couponId, name, description, discount, endAt);
        }

        @Override
        public String toString() {
            return "FindIssuedCouponResponse{" +
                    "couponId=" + couponId +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", discount=" + discount +
                    ", endAt=" + endAt +
                    '}';
        }
    }
}