package org.example.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.config.AuthUser;
import org.example.domain.user.dto.request.DeleteUserRequestDto;
import org.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "회원탈퇴 기능")
public class UserController {

    private final UserService userService;

    // 회원 탈퇴
    @DeleteMapping
    @Operation(summary = "회원탈퇴")
    @ApiResponse(responseCode = "200", description = "회원탈퇴 성공")
    public void deletedUser (@Parameter(description = "비밀번호 입력")
                             @AuthenticationPrincipal AuthUser authUser,
                             @RequestBody DeleteUserRequestDto deleteUserRequestDto){
        userService.deletedUser(authUser, deleteUserRequestDto);
    }

}
