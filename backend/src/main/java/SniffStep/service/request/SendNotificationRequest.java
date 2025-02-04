package SniffStep.service.request;

import SniffStep.entity.NotificationType;

public record SendNotificationRequest(Long memberId, String title, String content, NotificationType notificationType) {

    public static SendNotificationRequest of(Long memberId, String title, String content, NotificationType notificationType) {
        return new SendNotificationRequest(memberId, title, content, notificationType);
    }
}
