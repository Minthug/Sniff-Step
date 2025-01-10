package SniffStep.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class RegisterCouponRequest {
    @NotBlank(message = "쿠폰 이름은 필수 입력 사항입니다.")
    private String name;

    @Positive(message = "할인 금액은 양수 이어야합니다")
    @NotNull(message = "할인 금액은 필수 입력 사항입니다.")
    private Integer discount;

    private String description;

    @NotNull(message = "쿠폰 종료일은 필수 입력 사항입니다.")
    private LocalDate endAt;

    public RegisterCouponRequest() {
    }

    public RegisterCouponRequest(String name, Integer discount, String description, LocalDate endAt) {
        this.name = name;
        this.discount = discount;
        this.description = description;
        this.endAt = endAt;
    }

    // Getter와 Setter 메서드들
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDate endAt) {
        this.endAt = endAt;
    }
}
}
