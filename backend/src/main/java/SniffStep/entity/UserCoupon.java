package SniffStep.entity;

import SniffStep.common.BaseTime;
import SniffStep.common.exception.InvalidUsedCouponException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserCoupon extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public UserCoupon(Member member, Coupon coupon) {
        this.isUsed = false;
        this.member = member;
        this.coupon = coupon;
    }

    public int getDiscount() {
        return this.getCoupon().getDiscount();
    }

    public void use() {
        if (isUsed == true) {
            throw new InvalidUsedCouponException("이미 사용한 쿠폰 입니다");
        }
        isUsed = true;
    }

    public void unUse() {
        if (isUsed == false) {
            throw new InvalidUsedCouponException("사용하지 않은 쿠폰 입니다");
        }
        isUsed = false;
    }
}
