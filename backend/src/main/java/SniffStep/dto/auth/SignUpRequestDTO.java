package SniffStep.dto.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDTO {

    @NotNull(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Nickname cannot be blank")
    private String nickname;

    private String introduce;

    private String phoneNumber;

    private String imageUrl;

}
