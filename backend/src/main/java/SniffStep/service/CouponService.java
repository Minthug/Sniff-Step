package SniffStep.service;

import SniffStep.common.exception.InvalidUsedCouponException;
import SniffStep.common.exception.NotFoundException;
import SniffStep.entity.Coupon;
import SniffStep.entity.Member;
import SniffStep.entity.UserCoupon;
import SniffStep.repository.CouponRepository;
import SniffStep.repository.MemberRepository;
import SniffStep.repository.UserCouponRepository;
import SniffStep.service.request.RegisterCouponRequest;
import SniffStep.service.request.RegisterUserCouponRequest;
import SniffStep.service.response.FindCouponsResponse;
import SniffStep.service.response.FindIssuedCouponsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Long createCoupon(RegisterCouponRequest request) {
        Coupon coupon = Coupon.builder()
                .name(request.name())
                .discount(request.discount())
                .description(request.description())
                .endAt(request.endAt())
                .build();
        return couponRepository.save(coupon).getId();
    }

    @Transactional
    public Long registerUserCoupon(RegisterUserCouponRequest request) {
        Member findMember = findMemberByMemberId(request.memberId());
        Coupon findCoupon = findCouponByCouponId(request.couponId());

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
