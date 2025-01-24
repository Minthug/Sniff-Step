package SniffStep.dto.auth;

import jakarta.validation.constraints.NotNull;

public record SignUpRequest(@NotNull(message = "Email cannot be blank") String email,
                            @NotNull(message = "Password cannot be blank") String password,
                            @NotNull@NotNull(message = "Nickname cannot be blank") String nickname,
                            String introduce,
                            String phoneNumber,
                            String imageUrl) {

}
