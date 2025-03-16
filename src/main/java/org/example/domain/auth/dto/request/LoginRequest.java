package org.example.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    // 로그인 요청 값
    @NotBlank
    @Schema(description = "사용자 이름", example = "JIN HO")
    private String username;
    @NotBlank
    @Schema(description = "비밀번호",example = "123456~!")
    private String password;
}
