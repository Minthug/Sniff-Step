package SniffStep.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPostDTO {

    private String email;
    private String password;
    private String nickname;
}
