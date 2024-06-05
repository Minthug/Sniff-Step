package SniffStep.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@SpringBootTest

class RedisServiceTest {

    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);

    @Autowired
    private RedisService redisService;

    @BeforeEach
    void shutDown() {
        redisService.setValues(KEY, VALUE, DURATION);
    }

    @AfterEach
    void tearDown() {
        redisService.deleteValues(KEY);
    }

    @Test
    void saveAndFindTest() throws Exception {

        String findValue = redisService.getValues(KEY);

        assertThat(VALUE).isEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 값이 수정되는지 테스트")
    void updateTest() throws Exception {
        // given
        String updateValue = "updateValue";
        redisService.setValues(KEY, updateValue, DURATION);

        // when
        String findValue = redisService.getValues(KEY);

        // then
        assertThat(updateValue).isEqualTo(findValue);
        assertThat(VALUE).isNotEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis에 저장된 값이 삭제되는지 테스트")
    void deleteTest() throws Exception {
        // when
        redisService.deleteValues(KEY);
        String findValue = redisService.getValues(KEY);

        // then
        assertThat(findValue).isEqualTo("false");
    }

    @Test
    @DisplayName("Redis에 저장된 값이 만료되는지 테스트")
    void expireTest() throws Exception {
        String findValue = redisService.getValues(KEY);
        Thread.sleep(6000);
            String expiredValue = redisService.getValues(KEY);
            assertThat(expiredValue).isNotEqualTo(findValue);
            assertThat(expiredValue).isEqualTo("false");
        }
}