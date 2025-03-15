package org.example.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {
    // 로그인 반환 값
    private final String token;

    public LoginResponse(String bearerToken) {
        this.token = bearerToken.substring(7);
    }
}
