package org.example.config;

import org.example.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class AuthUser {

    private final Long userId;

    private final String nickname;


    private final UserRole userRole;


    public AuthUser(Long userId, String nickname, UserRole userRole) {
        this.userId = userId;
        this.nickname = nickname;
        this.userRole = userRole;
    }

}