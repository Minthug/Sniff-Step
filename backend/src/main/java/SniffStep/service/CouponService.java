package SniffStep.service;

import SniffStep.entity.Coupon;
import SniffStep.repository.CouponRepository;
import SniffStep.repository.MemberRepository;
import SniffStep.repository.UserCouponRepository;
import SniffStep.service.request.RegisterCouponCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
