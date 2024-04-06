package SniffStep.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinDTO {


    @Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @Length(min = 2, max = 8)
    private String nickname;
    private String phoneNumber;
    @NotBlank
    private String password;

    @Builder
    public JoinDTO(String email, String name, String nickname, String phoneNumber, String password) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
