package org.example.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.common.enums.ErrorStatus;
import org.example.common.exception.ApiException;
import org.example.config.JwtUtil;
import org.example.domain.auth.dto.request.LoginRequest;
import org.example.domain.auth.dto.request.SignupRequest;
import org.example.domain.auth.dto.response.LoginResponse;
import org.example.domain.auth.dto.response.SignupResponse;
import org.example.domain.user.entity.User;
import org.example.domain.user.enums.UserRole;
import org.example.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    // 비밀번호 유효성 검사 정규 표현식
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입
    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        // 이름 중복 확인
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new ApiException(ErrorStatus._USER_ALREADY_EXISTS);
        }

        // 비밀번호 형식 유효성 검사
        if (!isValidPassword(signupRequest.getPassword())) {
            throw new ApiException(ErrorStatus._INVALID_PASSWORD_FORM);
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.USER; // 기본값은 USER
        if ("ADMIN".equals(signupRequest.getRole())) {
            userRole = UserRole.ADMIN; // ADMIN일 경우 ROLE_ADMIN 설정
        }

        User newUser = new User(
                signupRequest.getUsername(),
                signupRequest.getNickname(),
                encodedPassword,
                userRole
        );

        // 유저 생성 후 저장.
        User savedUser = userRepository.save(newUser);

        return new SignupResponse(savedUser.getUsername(), savedUser.getNickname(), savedUser.getUserRole().toString());
    }

    // 비밀번호 유효성 검사 메서드
    private boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    // 로그인
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
                () -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));

        // 로그인 시 비밀번호가 일치하지 않을 경우 401을 반환합니다.
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorStatus._PASSWORD_NOT_MATCHES);
        }

        // 탈퇴한 유저일 경우 로그인 불가
        if (user.getIsdeleted()) {
            throw new ApiException(ErrorStatus._DELETED_USER);
        }

        String bearerToken = jwtUtil.createToken(user.getUserId(), user.getNickname(), user.getUserRole());

        return new LoginResponse(bearerToken);
    }

}
