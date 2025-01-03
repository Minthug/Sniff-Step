package SniffStep.service.response;

import SniffStep.entity.Notification;
import SniffStep.entity.NotificationType;

import java.time.LocalDateTime;
import java.util.Objects;

public class NotificationResponse {
    private final String title;
    private final String content;
    private final NotificationType notificationType;
    private final Long memberId;
    private final LocalDateTime createdAt;

    public NotificationResponse(String title, String content, NotificationType notificationType, Long memberId, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.memberId = memberId;
        this.createdAt = createdAt;
    }

    public static NotificationResponse from(Notification notification) {
        return new NotificationResponse(
                notification.getTitle(),
                notification.getContent(),
                notification.getNotificationType(),
                notification.getMemberId(),
                notification.getCreatedAt());
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationResponse that = (NotificationResponse) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                notificationType == that.notificationType &&
                Objects.equals(memberId, that.memberId) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, notificationType, memberId, createdAt);
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", notificationType=" + notificationType +
                ", memberId=" + memberId +
                ", createdAt=" + createdAt +
                '}';
    }
}