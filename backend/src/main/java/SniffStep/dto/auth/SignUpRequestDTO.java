package SniffStep.dto.auth;


import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDTO {

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

//    @Size(min = 2, max = 10, message = "Name must be between 2 and 10 characters")
//    private String name;

    private String nickname;

    private String introduce;

    private String phoneNumber;

    private String imageUrl;

}
