package SniffStep.common.exception;

import lombok.Getter;
import org.springframework.data.repository.cdi.Eager;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "회원 정보를 찾을 수 없습니다."),
    MEMBER_EXISTS(409, "회원 정보가 이미 존재합니다."),
    POST_NOT_FOUND(404, "게시글을 찾을 수 없습니다."),
    FILE_NOT_FOUND(404, "이미지를 찾을 수 없습니다"),
    FILE_TYPE_NOT_ACCEPTED(406, "허용되지 않은 타입의 파일입니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
