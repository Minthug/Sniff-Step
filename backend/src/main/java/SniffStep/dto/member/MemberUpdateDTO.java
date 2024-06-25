package SniffStep.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MemberUpdateDTO {
    private String nickname;
    private String introduce;
    private String password;
    private String phoneNumber;
    private String imageUrl;

}
