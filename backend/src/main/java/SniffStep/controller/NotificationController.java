package SniffStep.controller;

import SniffStep.service.NotificationService;
import SniffStep.service.request.ConnectNotificationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
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
        ConnectNotificationCommand command = ConnectNotificationCommand.of(memberId, lastEventId);
        SseEmitter sseEmitter = notificationService.connectNotification(command);
        return ResponseEntity.ok(sseEmitter);

    }
}
