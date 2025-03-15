package org.example.domain.user.controller;

import org.example.config.AuthUser;
import org.example.domain.user.dto.request.DeleteUserRequestDto;
import org.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원 탈퇴
    @DeleteMapping
    public void deletedUser (@AuthenticationPrincipal AuthUser authUser, @RequestBody DeleteUserRequestDto deleteUserRequestDto){
        userService.deletedUser(authUser, deleteUserRequestDto);
    }

}
