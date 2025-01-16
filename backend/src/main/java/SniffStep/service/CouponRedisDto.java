package SniffStep.service;

import SniffStep.entity.Coupon;

import java.time.LocalDate;
import java.util.Objects;

public class CouponRedisDto {
    private final Long couponId;
    private final Integer discount;
    private final String name;
    private final String description;
    private final LocalDate endAt;
    private final Integer minOrderPrice;

    public CouponRedisDto(Long couponId, Integer discount, String name, String description, LocalDate endAt, Integer minOrderPrice) {
        this.couponId = couponId;
        this.discount = discount;
        this.name = name;
        this.description = description;
        this.endAt = endAt;
        this.minOrderPrice = minOrderPrice;
    }

    public static CouponRedisDto from(final Coupon coupon) {
        return new CouponRedisDto(
                coupon.getId(),
                coupon.getDiscount(),
                coupon.getName(),
                coupon.getDescription(),
                coupon.getEndAt(),
                coupon.getMinOrderPrice()
        );
    }

    // Getter 메서드들
    public Long getCouponId() {
        return couponId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public Integer getMinOrderPrice() {
        return minOrderPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponRedisDto that = (CouponRedisDto) o;
        return Objects.equals(couponId, that.couponId) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(endAt, that.endAt) &&
                Objects.equals(minOrderPrice, that.minOrderPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponId, discount, name, description, endAt, minOrderPrice);
    }

    @Override
    public String toString() {
        return "CouponRedisDto{" +
                "couponId=" + couponId +
                ", discount=" + discount +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", endAt=" + endAt +
                ", minOrderPrice=" + minOrderPrice +
                '}';
    }
}