package org.example.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.domain.auth.dto.request.LoginRequest;
import org.example.domain.auth.dto.request.SignupRequest;
import org.example.domain.auth.dto.response.LoginResponse;
import org.example.domain.auth.dto.response.SignupResponse;
import org.example.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입/로그인 관리기능")
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    public SignupResponse signup(@Valid
                                 @RequestBody
                                 @Parameter(description = "회원정보 입력")
                                 SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    // 로그인
    @Operation(summary = "로그인")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @PostMapping("/login")
    public LoginResponse login(@Valid
                               @RequestBody
                               @Parameter(description = "로그인정보 입력")
                               LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
