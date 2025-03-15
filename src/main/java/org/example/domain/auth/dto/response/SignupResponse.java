package org.example.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.user.enums.UserRole;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {

    private String username;    // 사용자 이름
    private String nickname;    // 사용자 닉네임
    private String role;  // 사용자 권한 목록
}
