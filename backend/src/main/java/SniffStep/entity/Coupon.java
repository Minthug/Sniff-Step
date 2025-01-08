package SniffStep.entity;

import SniffStep.common.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.InvalidParameterException;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Coupon extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer discount;

    private String name;

    private String description;

    private LocalDate endAt;

    private Integer minOrderPrice;

    @Builder
    public Coupon(Integer discount, String name, String description, LocalDate endAt, Integer minOrderPrice) {
        this.discount = discount;
        this.name = name;
        this.description = description;
        this.endAt = endAt;
        this.minOrderPrice = minOrderPrice;
    }

    private void validateEndAt(LocalDate endAt) {
        LocalDate currentDate = LocalDate.now();
        if (endAt.isBefore(currentDate)) {
            throw new InvalidParameterException("쿠폰 종료일은 현재 날짜보다 이전일 수 없습니다");
        }
    }
}
