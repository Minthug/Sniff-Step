package SniffStep.service.response;

import SniffStep.entity.Notification;
import SniffStep.entity.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponseCommand(String title, String content, NotificationType notificationType,
                                          Long memberId, LocalDateTime createdAt) {

    public static NotificationResponseCommand from(Notification notification) {
        return new NotificationResponseCommand(
                notification.getTitle(),
                notification.getContent(),
                notification.getNotificationType(),
                notification.getMemberId(),
                notification.getCreatedAt()
        );
    }
}