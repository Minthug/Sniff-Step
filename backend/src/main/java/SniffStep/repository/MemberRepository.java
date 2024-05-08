package SniffStep.repository;

import SniffStep.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByLoginId(String loginId);

    Member findByLoginId(String loginId);

    boolean existsByEmail(String email);

    Member findByName(String name);

}
