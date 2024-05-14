package SniffStep.repository;

import SniffStep.common.jwt.entity.RefreshToken;
import SniffStep.common.jwt.entity.Token;
import SniffStep.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
    Optional<RefreshToken> findByKey(String key);
}