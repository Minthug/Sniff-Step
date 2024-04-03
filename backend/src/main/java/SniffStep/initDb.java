package SniffStep;

import SniffStep.member.entity.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("TESTMock", "jkjk@gmail.com", "KK", "010-1234-5678", "123444");
            em.persist(member);
        }

        private Member createMember(String name, String email, String nickname, String phoneNumber, String password) {
            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setNickname(nickname);
            member.setPhoneNumber(phoneNumber);
            member.setPassword(password);
            return member;
        }
    }
}
