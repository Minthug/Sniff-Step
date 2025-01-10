package SniffStep.repository;

import SniffStep.entity.Coupon;
import SniffStep.entity.Member;
import SniffStep.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findByIdWithCoupon(Long id);

    boolean existsByMemberAndCoupon(Member member, Coupon coupon);

    List<UserCoupon> findByMemberAndIsUsedAndCouponEndAtAfter(@Param("member") Member member,
                                                              @Param("isUsed") boolean isUsed,
                                                              @Param("currentDate")LocalDate localDate);

    void deleteByMember(Member findMember);
}
