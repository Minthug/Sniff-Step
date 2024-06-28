package SniffStep.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityTime {

    MORNING("오전"),
    AFTERNOON("오후"),
    EVENING("저녁");

    private final String value;

    @JsonCreator
    public static ActivityTime fromString(String key) {
        return key == null
                ? null
                : ActivityTime.valueOf(key.toUpperCase());
    }
}
