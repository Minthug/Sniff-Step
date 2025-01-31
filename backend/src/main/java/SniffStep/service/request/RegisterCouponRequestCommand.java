package SniffStep.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record RegisterCouponRequestCommand(@NotBlank(message = "쿠폰 이름은 필수 입력 사항입니다") String name,
                                           @Positive(message = "할인 금액은 양수 이어야합니다.")
                                           @NotNull(message = "할인 금액은 필수 입력 사항입니다") Integer discount,
                                           String description,
                                           @NotNull(message = "쿠폰 종료일은 필수 입력 사항입니다")
                                           LocalDate endAt) {
}
