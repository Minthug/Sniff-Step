package SniffStep.entity;

import SniffStep.common.BaseTime;
import SniffStep.common.exception.InvalidNotificationException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTime {

    private static final int TITLE_LENGTH = 30;
    private static final int CONTENT_LENGTH = 100;

    private String title;
    private String content;
    private NotificationType notificationType;
    private Long memberId;

    @Builder
    public Notification(String title, String content, NotificationType notificationType, Long memberId) {
        this.title = title;
        this.content = content;
        this.notificationType = notificationType;
        this.memberId = memberId;
    }

    private void validateContent(String content) {
        if (Objects.nonNull(content) && content.length() > CONTENT_LENGTH) {
            throw new InvalidNotificationException("내용은 50글자 이하이어야 합니다");
        }
    }

    private void validateTitle(String title) {
        if (Objects.nonNull(title) && content.length() > TITLE_LENGTH) {
            throw new InvalidNotificationException("제목의 길이는 20자 이하이어야 합니다");
        }
    }
}
