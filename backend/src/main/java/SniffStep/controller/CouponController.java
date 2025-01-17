package SniffStep.controller;

import SniffStep.service.CouponService;
import SniffStep.service.request.RegisterCouponCommand;
import SniffStep.service.request.RegisterCouponRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
