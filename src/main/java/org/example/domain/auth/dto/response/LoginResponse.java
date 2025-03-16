package org.example.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginResponse {
    // 로그인 반환 값
    @Schema(description = "토큰",example = "Access Token")
    private final String token;

    public LoginResponse(String bearerToken) {
        this.token = bearerToken.substring(7);
    }
}
