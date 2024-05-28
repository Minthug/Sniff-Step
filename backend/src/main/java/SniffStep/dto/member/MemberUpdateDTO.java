package SniffStep.dto.member;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MemberUpdateDTO {
    private String nickname;
    private String introduce;
    private String password;

}
