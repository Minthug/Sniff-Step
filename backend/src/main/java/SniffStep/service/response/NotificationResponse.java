package SniffStep.service.response;

import SniffStep.entity.Notification;
import SniffStep.entity.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponse(String title, String content, NotificationType notificationType,
                                          Long memberId, LocalDateTime createdAt) {

    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getTitle(),
                notification.getContent(),
                notification.getNotificationType(),
                notification.getMemberId(),
                notification.getCreatedAt()
        );
    }
}