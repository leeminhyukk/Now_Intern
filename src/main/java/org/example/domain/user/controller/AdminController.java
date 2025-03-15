package org.example.domain.user.controller;

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
public class AdminController {
    private final UserService userService;

    @PutMapping("/users/{userId}/roles")
    public UpdateUserAuthorityDto updateUserAuthority(@PathVariable Long userId,
                                                      @AuthenticationPrincipal AuthUser authUser) {
        return userService.updateUserAuthority(userId, authUser);
    }
}
