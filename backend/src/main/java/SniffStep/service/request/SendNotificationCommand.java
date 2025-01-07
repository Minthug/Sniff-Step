package SniffStep.service.request;

import SniffStep.entity.NotificationType;
import lombok.Builder;

import java.util.Objects;

@Builder
public class SendNotificationCommand {
    private final Long memberId;
    private final String title;
    private final String content;
    private final NotificationType notificationType;

    public SendNotificationCommand(Long memberId, String title, String content, NotificationType notificationType) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
    }

    public static SendNotificationCommand of(final Long memberId, final String title, final String content,
                                             final NotificationType notificationType) {
        return new SendNotificationCommand(memberId, title, content, notificationType);
    }

    public Long getMemberId() {
        return memberId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendNotificationCommand that = (SendNotificationCommand) o;
        return Objects.equals(memberId, that.memberId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                notificationType == that.notificationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, title, content, notificationType);
    }

    @Override
    public String toString() {
        return "SendNotificationCommand{" +
                "memberId=" + memberId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", notificationType=" + notificationType +
                '}';
    }
}