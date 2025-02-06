package SniffStep.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String CACHE_KEY_PREFIX = "coupon:light";
    private static final String COUPON_INFO_PREFIX = "coupon:info";
    private static final String AVAILABLE_COUPON_KEY = "coupons:available";

    public void cacheCoupon(CouponResponse response) {
        try {
            String key = CACHE_KEY_PREFIX + response.id();
            String value = objectMapper.writeValueAsString(response);
            redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to cache coupon", ex);
        }
    }

    public void bulkCacheCouponInfo(List<CouponRedisResponse> coupons) {
        Map<String, CouponRedisResponse> couponMap = coupons.stream()
                .collect(Collectors.toMap(
                        dto -> COUPON_INFO_PREFIX + dto.couponId(),
                        dto -> dto
                ));
        redisTemplate.opsForValue().multiSet(couponMap);
    }

    public List<CouponRedisResponse> getCouponsByIds(List<Long> couponIds) {
        List<String> keys = couponIds.stream()
                .map(id -> COUPON_INFO_PREFIX + id)
                .collect(Collectors.toList());
        List<Object> values = redisTemplate.opsForValue().multiGet(keys);

        return values.stream()
                .filter(Objects::nonNull)
                .map(obj -> (CouponRedisResponse) obj)
                .collect(Collectors.toList());
    }

    public void logCacheStatus(String operation, boolean isHit) {
        log.info("Cache {} - {}: {}", operation, isHit ? "HIT" : "MISS",
                MDC.get("requestId"));
    }

    public Optional<CouponResponse> getCacheCoupon(Long couponId) {
        try {
            String key = CACHE_KEY_PREFIX + couponId;
            String value = (String) redisTemplate.opsForValue().get(key);
            if (value == null) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(value, CouponResponse.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to get cached coupon", e);
        }
    }

    public void deleteCachedCoupon(Long couponId) {
        String key = CACHE_KEY_PREFIX + couponId;
        redisTemplate.delete(key);
    }
}
