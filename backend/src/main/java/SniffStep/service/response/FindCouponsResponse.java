package SniffStep.service.response;

import SniffStep.entity.Coupon;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FindCouponsResponse {
    private final List<FindCouponResponse> coupons;

    public FindCouponsResponse(List<FindCouponResponse> coupons) {
        this.coupons = coupons;
    }

    public static FindCouponsResponse from(final List<Coupon> coupons) {
        return coupons.stream()
                .map(FindCouponResponse::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FindCouponsResponse::new));
    }

    public List<FindCouponResponse> getCoupons() {
        return coupons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FindCouponsResponse that = (FindCouponsResponse) o;
        return Objects.equals(coupons, that.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coupons);
    }

    @Override
    public String toString() {
        return "FindCouponsResponse{" +
                "coupons=" + coupons +
                '}';
    }

    public static class FindCouponResponse {
        private final Long couponId;
        private final String name;
        private final String description;
        private final Integer discount;
        private final LocalDate endAt;

        public FindCouponResponse(Long couponId, String name, String description, Integer discount, LocalDate endAt) {
            this.couponId = couponId;
            this.name = name;
            this.description = description;
            this.discount = discount;
            this.endAt = endAt;
        }

        public static FindCouponResponse from(final Coupon coupon) {
            return new FindCouponResponse(
                    coupon.getId(),
                    coupon.getName(),
                    coupon.getDescription(),
                    coupon.getDiscount(),
                    coupon.getEndAt());
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
            FindCouponResponse that = (FindCouponResponse) o;
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
            return "FindCouponResponse{" +
                    "couponId=" + couponId +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", discount=" + discount +
                    ", endAt=" + endAt +
                    '}';
        }
    }
}