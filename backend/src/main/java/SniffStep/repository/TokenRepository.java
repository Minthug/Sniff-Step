package SniffStep.repository;

import SniffStep.common.jwt.entity.Token;
import SniffStep.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {


    Optional<Token> findByRefreshToken(final String refreshToken);

    Optional<Token> findByMember(final Member member);

    @Query("DELETE FROM Token t where t.member.id = :memberId")
    @Modifying
    void deleteByMemberId(final Long memberId);
}
