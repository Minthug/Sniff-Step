package SniffStep.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@Getter
@RequiredArgsConstructor
public enum NotificationMessage {

    NEW_EVENT("뉴 이벤트"),
    DEADLINE("마감 임박");

    private final String title;
    private final String contentFormat;

    public String getContentFromFormat(Object... arguments) {
        return MessageFormat.format(contentFormat, arguments);
    }
}
