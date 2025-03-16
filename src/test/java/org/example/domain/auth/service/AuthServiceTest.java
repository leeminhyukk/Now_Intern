package org.example.domain.auth.service;

import org.example.config.JwtUtil;
import org.example.domain.auth.dto.request.LoginRequest;
import org.example.domain.auth.dto.request.SignupRequest;
import org.example.domain.auth.dto.response.LoginResponse;
import org.example.domain.auth.dto.response.SignupResponse;
import org.example.domain.user.entity.User;
import org.example.domain.user.enums.UserRole;
import org.example.domain.user.repository.UserRepository;
import org.example.common.exception.ApiException;
import org.example.common.enums.ErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;

    private User user;

    @BeforeEach
    public void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this); // Mockito 초기화 추가

        // Test data setup
        signupRequest = new SignupRequest("testuser", "testNickname", "ValidP@ssw0rd", "USER");
        loginRequest = new LoginRequest("testuser", "ValidP@ssw0rd");

        user = new User("testuser", "testNickname", "EncodedPassword", UserRole.USER);
    }

    @Test
    public void testSignup_success() {
        // Given
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("EncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        SignupResponse response = authService.signup(signupRequest);

        // Then
        assertNotNull(response); // 응답이 null이 아님
        assertEquals("testuser", response.getUsername());
        assertEquals("testNickname", response.getNickname());
        assertEquals("USER", response.getRole());
    }

    @Test
    public void testSignup_userAlreadyExists() {
        // Given
        when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(true);

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            authService.signup(signupRequest);
        });
        assertEquals(ErrorStatus._USER_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    public void testLogin_success() {
        // Given
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.createToken(user.getUserId(), user.getNickname(), user.getUserRole())).thenReturn("Bearer testToken");

        // When
        LoginResponse response = authService.login(loginRequest);

        // Then
        assertNotNull(response); // 응답이 null이 아님
        assertEquals("testToken", response.getToken());
    }

    @Test
    public void testLogin_userNotFound() {
        // Given
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(java.util.Optional.empty());

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            authService.login(loginRequest);
        });
        assertEquals(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER, exception.getErrorCode());
    }

    @Test
    public void testLogin_passwordNotMatch() {
        // Given
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            authService.login(loginRequest);
        });
        assertEquals(ErrorStatus._PASSWORD_NOT_MATCHES, exception.getErrorCode());
    }

    @Test
    public void testLogin_userDeleted() throws NoSuchFieldException, IllegalAccessException {
        // Given
        User user = new User("testuser", "testNickname", "encodedPassword", UserRole.USER); // 유저 생성
        setIsDeletedTrue(user);  // 리플렉션을 사용하여 isdeleted 값을 true로 설정

        LoginRequest loginRequest = new LoginRequest("testuser", "password");

        // Mocking
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        // When & Then
        ApiException exception = assertThrows(ApiException.class, () -> {
            authService.login(loginRequest);
        });
        assertEquals(ErrorStatus._DELETED_USER, exception.getErrorCode());
    }

    private void setIsDeletedTrue(User user) throws NoSuchFieldException, IllegalAccessException {
        // 리플렉션을 사용하여 isdeleted 값을 true로 설정
        Field isDeletedField = User.class.getDeclaredField("isdeleted");
        isDeletedField.setAccessible(true);  // private 필드에 접근할 수 있도록 설정
        isDeletedField.set(user, true);  // isdeleted 값을 true로 설정
    }
}
