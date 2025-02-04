package SniffStep.service;

import SniffStep.entity.Notification;
import SniffStep.entity.NotificationType;
import SniffStep.repository.EmitterRepository;
import SniffStep.repository.MemberRepository;
import SniffStep.service.request.ConnectNotificationCommand;
import SniffStep.service.request.SendNotificationRequest;
import SniffStep.service.response.NotificationResponse;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 120;

    private final EmitterRepository emitterRepository;
    private final MemberRepository memberRepository;


    public SseEmitter connectNotification(ConnectNotificationCommand command) {
        Long memberId = command.getMemberId();
        String lastEventId = command.getLastEventId();

        String emitterId = format("{0}_{1}", memberId, System.currentTimeMillis());
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(emitterId, sseEmitter);

        sseEmitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        sseEmitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        sseEmitter.onError(e -> emitterRepository.deleteById(emitterId));


        send(sseEmitter, emitterId, format("[connected] MemberId={0}", memberId));

        if (!command.getLastEventId().isEmpty()) {
            Map<String, SseEmitter> events = emitterRepository.findAllByIdStartWith(memberId);
            events.entrySet().stream().filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> send(sseEmitter, emitterId, entry.getValue()));
        }
        return sseEmitter;
    }

    public void sendNotification(SendNotificationRequest request) {
        Long memberId = request.memberId();
        String title = request.title();
        String content = request.content();
        NotificationType notificationType = request.notificationType();
        verifyExistsUser(memberId);
        Notification notification = Notification.builder()
                .title(title)
                .content(content)
                .memberId(memberId)
                .notificationType(notificationType)
                .build();

        Map<String, SseEmitter> emitters = emitterRepository.findAllByIdStartWith(memberId);
        emitters.forEach((key, emitter) -> {
            send(emitter, key, NotificationResponse.from(notification));
        });
    }

    private void verifyExistsUser(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않은 유저 입니다"));
    }

    /**
     * 알림 객체 전송용
     * @param emitter
     * @param emitterId
     * @param data
     */
    private void send(SseEmitter emitter, String emitterId, NotificationResponse data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name(data.notificationType().getValue())
                    .data(data));
        } catch (IOException | java.io.IOException ex) {
            emitterRepository.deleteById(emitterId);
        }
    }


    /**
     *
     * 일반 데이터 전송용
     * @param emitter
     * @param emitterId
     * @param data
     */
    private void send(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException | java.io.IOException e) {
            emitterRepository.deleteById(emitterId);
            log.error("알림 전송에 실패했습니다", e);
        }
    }
}
