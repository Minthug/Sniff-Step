package SniffStep.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateDTO {
    private String nickname;
    private String introduce;
    private String password;
    
    @Builder
    public MemberUpdateDTO(String nickname, String introduce, String password) {
        this.nickname = nickname;
        this.introduce = introduce;
        this.password = password;
    }
}
