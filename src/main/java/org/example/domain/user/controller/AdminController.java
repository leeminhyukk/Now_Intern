package org.example.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.config.AuthUser;
import org.example.domain.user.dto.response.UpdateUserAuthorityDto;
import org.example.domain.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "관리자 권한 부여기능")
public class AdminController {
    private final UserService userService;

    @PutMapping("/users/{userId}/roles")
    @Operation(summary = "관리자 권한 부여")
    @ApiResponse(responseCode = "200", description = "관리자 권한 부여 성공")
    public UpdateUserAuthorityDto updateUserAuthority(@PathVariable Long userId,
                                                      @AuthenticationPrincipal AuthUser authUser) {
        return userService.updateUserAuthority(userId, authUser);
    }
}
