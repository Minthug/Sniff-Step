package SniffStep.api;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginForm {

    @NotEmpty(message = "이메일은 필수 입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입니다.")
    private String password;
}
