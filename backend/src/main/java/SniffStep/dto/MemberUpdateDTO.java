package SniffStep.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateDTO {
    private String email;
    private String password;
    
    @Builder
    public MemberUpdateDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
