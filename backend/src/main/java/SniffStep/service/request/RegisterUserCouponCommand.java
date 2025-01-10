package SniffStep.service.request;

import java.util.Objects;

public class RegisterUserCouponCommand {

    private Long memberId;
    private Long couponId;

    public RegisterUserCouponCommand(Long memberId, Long couponId) {
        this.memberId = memberId;
        this.couponId = couponId;
    }

    public static RegisterUserCouponCommand of(final Long memberId, final Long couponId) {
        return new RegisterUserCouponCommand(memberId, couponId);
    }

    // Getter 메서드들
    public Long getMemberId() {
        return memberId;
    }

    public Long getCouponId() {
        return couponId;
    }

    // equals() 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterUserCouponCommand that = (RegisterUserCouponCommand) o;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(couponId, that.couponId);
    }

    // hashCode() 메서드
    @Override
    public int hashCode() {
        return Objects.hash(memberId, couponId);
    }

    // toString() 메서드
    @Override
    public String toString() {
        return "RegisterUserCouponCommand{" +
                "memberId=" + memberId +
                ", couponId=" + couponId +
                '}';
    }
}
