package SniffStep.repository;

import SniffStep.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByEndAtGreaterThanEqual(LocalDate localDate);

    List<Coupon> findByEndAtBefore(LocalDate localDate);
}
