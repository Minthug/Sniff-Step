package SniffStep.repository;

import SniffStep.entity.Coupon;
import SniffStep.entity.Member;
import SniffStep.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("SELECT uc from UserCoupon uc JOIN fetch uc.coupon c WHERE uc.id = :id")
    Optional<UserCoupon> findByIdWithCoupon(Long id);

    boolean existsByMemberAndCoupon(Member member, Coupon coupon);

    @Query("SELECT uc from UserCoupon uc JOIN FETCH uc.coupon c "
    + "WHERE uc.member = :member AND uc.isUsed = :isUsed AND c.endAt >= :currentDate")
    List<UserCoupon> findByMemberAndIsUsedAndCouponEndAtAfter(@Param("member") Member member,
                                                              @Param("isUsed") boolean isUsed,
                                                              @Param("currentDate")LocalDate localDate);

    void deleteByMember(Member findMember);
}
