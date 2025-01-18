package SniffStep.service;

import SniffStep.common.exception.InvalidUsedCouponException;
import SniffStep.common.exception.NotFoundException;
import SniffStep.entity.Coupon;
import SniffStep.entity.Member;
import SniffStep.entity.UserCoupon;
import SniffStep.repository.CouponRepository;
import SniffStep.repository.MemberRepository;
import SniffStep.repository.UserCouponRepository;
import SniffStep.service.request.RegisterCouponCommand;
import SniffStep.service.request.RegisterUserCouponCommand;
import SniffStep.service.response.FindCouponsResponse;
import SniffStep.service.response.FindIssuedCouponsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createCoupon(RegisterCouponCommand command) {
        Coupon coupon = Coupon.builder()
                .name(command.getName())
                .discount(command.getDiscount())
                .description(command.getDescription())
                .endAt(command.getEndAt())
                .build();
        return couponRepository.save(coupon).getId();
    }

    @Transactional
    public Long registerUserCoupon(RegisterUserCouponCommand command) {
        Member findMember = findMemberByMemberId(command.getMemberId());
        Coupon findCoupon = findCouponByCouponId(command.getCouponId());

        validateCouponExpiration(findCoupon.getEndAt());
        validateAlreadyIssuedCoupon(findMember, findCoupon);

        UserCoupon userCoupon = new UserCoupon(findMember, findCoupon);
        return userCouponRepository.save(userCoupon).getId();
    }

    @Transactional
    public FindCouponsResponse findCoupons() {
        List<Coupon> findCoupons = couponRepository.findByEndAtGreaterThanEqual(LocalDate.now());
        return FindCouponsResponse.from(findCoupons);
    }

    @Transactional(readOnly = true)
    public FindIssuedCouponsResponse findIssuedCoupons(Long memberId) {
        Member findMember = findMemberByMemberId(memberId);
        List<UserCoupon> findUserCoupons = userCouponRepository.findByMemberAndIsUsedAndCouponEndAtAfter(findMember, false, LocalDate.now());
        return FindIssuedCouponsResponse.from(findUserCoupons);
    }

    private void validateAlreadyIssuedCoupon(Member findMember, Coupon findCoupon) {
        if (userCouponRepository.existsByMemberAndCoupon(findMember, findCoupon)) {
                throw new InvalidUsedCouponException("이미 발급된 쿠폰입니다");
        }
    }

    private void validateCouponExpiration(LocalDate expirationDate) {
        if (expirationDate.isBefore(LocalDate.now())) {
            throw new InvalidUsedCouponException("쿠폰이 이미 만료 되었습니다");
        }
    }

    private Coupon findCouponByCouponId(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 쿠폰 입니다"));
    }

    private Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자 입니다"));
    }
}
