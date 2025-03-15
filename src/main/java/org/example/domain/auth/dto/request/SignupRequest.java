package org.example.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    // 회원가입 요청 값

    @NotBlank
    private String username;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    private String role;
}
