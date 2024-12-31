package SniffStep.entity;

import SniffStep.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTime {

    private static final int TITLE_LENGTH = 30;
    private static final int CONTENT_LENGTH = 100;

    private String title;
    private String content;
    private NotficationType notficationType;
    private Long memberId;

}
