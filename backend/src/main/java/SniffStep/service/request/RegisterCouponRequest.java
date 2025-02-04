package SniffStep.service.request;

import java.time.LocalDate;

public record RegisterCouponRequest(String name, Integer discount, String description, LocalDate endAt) {
    public static RegisterCouponRequest from(RegisterCouponRequestCommand request) {
        return new RegisterCouponRequest(
                request.name(),
                request.discount(),
                request.description(),
                request.endAt()
        );
    }
}
