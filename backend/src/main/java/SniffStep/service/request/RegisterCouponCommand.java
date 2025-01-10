package SniffStep.service.request;

import java.time.LocalDate;
import java.util.Objects;

public class RegisterCouponCommand {
    private final String name;
    private final Integer discount;
    private final String description;
    private final LocalDate endAt;

    public RegisterCouponCommand(String name, Integer discount, String description, LocalDate endAt) {
        this.name = name;
        this.discount = discount;
        this.description = description;
        this.endAt = endAt;
    }

    public static RegisterCouponCommand from(RegisterCouponRequest request) {
        return new RegisterCouponCommand(
                request.getName(),
                request.getDiscount(),
                request.getDescription(),
                request.getEndAt()
        );
    }

    // Getter 메서드들
    public String getName() {
        return name;
    }

    public Integer getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    // equals() 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterCouponCommand that = (RegisterCouponCommand) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(description, that.description) &&
                Objects.equals(endAt, that.endAt);
    }

    // hashCode() 메서드
    @Override
    public int hashCode() {
        return Objects.hash(name, discount, description, endAt);
    }

    // toString() 메서드
    @Override
    public String toString() {
        return "RegisterCouponCommand{" +
                "name='" + name + '\'' +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                ", endAt=" + endAt +
                '}';
    }
}