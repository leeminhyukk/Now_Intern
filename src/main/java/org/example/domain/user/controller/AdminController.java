package org.example.domain.user.controller;

import org.example.common.dto.ApiResponse;
import org.example.config.AuthUser;
import org.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @PutMapping("/users/{userId}/roles")
    public ApiResponse<String> updateUserAuthority(@PathVariable Long userId,
                                                   @AuthenticationPrincipal AuthUser authUser) {
        userService.updateUserAuthority(userId, authUser);

        return ApiResponse.createSuccess("유저 권한 변경완료");
    }
}
