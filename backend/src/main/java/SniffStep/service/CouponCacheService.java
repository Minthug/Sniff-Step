package SniffStep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CouponCacheService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String CACHE_KEY_PREFIX = "coupon:light";

    public void cacheCoupon(CouponDTO couponDTO) {
        try {
            String key = CACHE_KEY_PREFIX + couponDTO.getId();
            String value = objectMapper.writeValueAsString(couponDTO);
            redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to cache coupon", ex);
        }
    }
}
