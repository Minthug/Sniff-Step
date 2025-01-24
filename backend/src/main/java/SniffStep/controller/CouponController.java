package SniffStep.controller;

import SniffStep.service.CouponService;
import SniffStep.service.request.RegisterCouponCommand;
import SniffStep.service.request.RegisterCouponRequest;
import SniffStep.service.request.RegisterUserCouponCommand;
import SniffStep.service.response.FindCouponsResponse;
import SniffStep.service.response.FindIssuedCouponsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/coupons")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody RegisterCouponRequest request) {
        RegisterCouponCommand command = RegisterCouponCommand.from(request);
        Long couponId = couponService.createCoupon(command);
        URI location = URI.create("/v1/coupons/" + couponId);
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/my-coupons/{couponId}")
    public ResponseEntity<Void> RegisterUserCoupon(@PathVariable(value = "couponId") final long couponId,
                                                   @PathVariable(value = "memberId") final long memberId) {
        RegisterUserCouponCommand userCouponCommand = RegisterUserCouponCommand.of(memberId, couponId);
        Long userCouponId = couponService.registerUserCoupon(userCouponCommand);

        URI location = URI.create("/v1/coupons/" + userCouponId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<FindCouponsResponse> findCoupons() {
        FindCouponsResponse findCouponsResponse = couponService.findCoupons();
        return ResponseEntity.ok(findCouponsResponse);
    }

    @GetMapping("/issued")
    public ResponseEntity<FindIssuedCouponsResponse> findIssuedCoupons(final long memberId) {
        FindIssuedCouponsResponse issuedCoupons = couponService.findIssuedCoupons(memberId);
        return ResponseEntity.ok(issuedCoupons);
    }
}
