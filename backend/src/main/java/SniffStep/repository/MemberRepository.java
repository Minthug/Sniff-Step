package SniffStep.repository;

import SniffStep.entity.Member;
import SniffStep.entity.MemberType;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);


    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByMemberTypeAndSocialId(MemberType memberType, String socialId);

    boolean existsByEmail(String email);

    Member findByName(String name);

}
