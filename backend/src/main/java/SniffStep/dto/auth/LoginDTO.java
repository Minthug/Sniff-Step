package SniffStep.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
