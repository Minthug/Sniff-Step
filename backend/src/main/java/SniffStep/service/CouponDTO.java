package SniffStep.service;

import SniffStep.entity.Coupon;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;

@RedisHash(value = "coupon", timeToLive = 60 * 60)
public class CouponDTO {

    private final Long id;
    private final String name;
    private final Integer discount;
    private final LocalDate endAt;

    public CouponDTO(Long id, String name, Integer discount, LocalDate endAt) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.endAt = endAt;
    }

    public static CouponDTO from(Coupon coupon) {
        return new CouponDTO(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscount(),
                coupon.getEndAt()
        );
    }

    // Getter 메서드들
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDiscount() {
        return discount;
    }

    public LocalDate getEndAt() {
        return endAt;
    }
}