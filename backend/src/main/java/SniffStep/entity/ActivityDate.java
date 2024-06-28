package SniffStep.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityDate {

    MON("월요일"),
    TUE("화요일"),
    WED("수요일"),
    THU("목요일"),
    FRI("금요일"),
    SAT("토요일"),
    SUN("일요일");


    private final String value;

    @JsonCreator
    public static ActivityDate fromString(String key) {
        return key == null
                ? null
                : ActivityDate.valueOf(key.toUpperCase());
    }
}
