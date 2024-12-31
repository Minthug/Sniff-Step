package SniffStep.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    CONNECT("connect"),
    NOTICE("notice");

    private final String value;
}
