package SniffStep.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateDTO {
    private String nickname;
    private String password;
    
    @Builder
    public MemberUpdateDTO(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
