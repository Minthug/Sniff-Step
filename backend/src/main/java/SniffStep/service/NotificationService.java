package SniffStep.service;

import SniffStep.repository.EmitterRepository;
import SniffStep.repository.MemberRepository;
import SniffStep.service.request.ConnectNotificationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 120;

    private final EmitterRepository emitterRepository;
    private final MemberRepository memberRepository;


    public SseEmitter connectNotification(ConnectNotificationCommand command) {
        Long memberId = command.memberId();
        return emitter;
    }
}
