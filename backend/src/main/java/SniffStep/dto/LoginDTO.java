package SniffStep.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String email;
    private String password;

}
