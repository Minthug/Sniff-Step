package SniffStep.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateDTO {
    private String nickname;
    private String introduce;
    private String password;

}
