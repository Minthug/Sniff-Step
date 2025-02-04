package SniffStep.controller;

import SniffStep.service.NotificationService;
import SniffStep.service.request.ConnectNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/connect", produces = "text/event-streams")
    public ResponseEntity<SseEmitter> sseConnection(@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        ConnectNotificationRequest request = ConnectNotificationRequest.of(memberId, lastEventId);
        SseEmitter sseEmitter = notificationService.connectNotification(request);
        return ResponseEntity.ok(sseEmitter);

    }
}
