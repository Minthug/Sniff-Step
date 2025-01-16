package SniffStep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CouponCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String CACHE_KEY_PREFIX = "coupon:light";
    private static final String COUPON_INFO_PREFIX = "coupon:info";
    private static final String AVAILABLE_COUPON_KEY = "coupons:available";

    public void cacheCouponInfo(CouponDTO couponDTO) {
        redisTemplate.opsForValue().set(
                COUPON_INFO_PREFIX + couponDTO.getId(),
                couponDTO,
                Duration.ofDays(1)
        );
    }

    public void cacheCoupon(CouponDTO couponDTO) {
        try {
            String key = CACHE_KEY_PREFIX + couponDTO.getId();
            String value = objectMapper.writeValueAsString(couponDTO);
            redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to cache coupon", ex);
        }
    }

    public Optional<CouponDTO> getCacheCoupon(Long couponId) {
        try {
            String key = CACHE_KEY_PREFIX + couponId;
            String value = (String) redisTemplate.opsForValue().get(key);
            if (value == null) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(value, CouponDTO.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to get cached coupon", e);
        }
    }

    public void deleteCachedCoupon(Long couponId) {
        String key = CACHE_KEY_PREFIX + couponId;
        redisTemplate.delete(key);
    }
}
