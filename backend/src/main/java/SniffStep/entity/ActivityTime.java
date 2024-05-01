package SniffStep.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityTime {

    MORNING("오전"),
    AFTERNOON("오후"),
    EVENING("저녁");

    private final String value;
}
