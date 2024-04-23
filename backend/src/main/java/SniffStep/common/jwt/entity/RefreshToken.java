package SniffStep.common.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "refresh_token_key")
    private String key;

    @Column(name = "refresh_token_value")
    private String value;

    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshToken updateValue(String refreshToken) {
        this.value = value;
        return this;
    }
}
