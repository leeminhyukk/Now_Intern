package org.example.domain.auth.controller;

import org.example.domain.auth.dto.request.LoginRequest;
import org.example.domain.auth.dto.request.SignupRequest;
import org.example.domain.auth.dto.response.LoginResponse;
import org.example.domain.auth.dto.response.SignupResponse;
import org.example.domain.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testSignup() {
        // Given
        String username = "testuser";
        String nickname = "testnickname";
        String password = "testpassword";
        String role = "USER"; // 또는 null이나 다른 적절한 값

        SignupRequest signupRequest = new SignupRequest(username, nickname, password, role);
        SignupResponse signupResponse = new SignupResponse("username",nickname,role);

        when(authService.signup(signupRequest)).thenReturn(signupResponse);

        // When
        SignupResponse response = authController.signup(signupRequest);

        // Then
        assertEquals("testnickname", response.getNickname());  // 반환된 응답이 예상한 메시지와 일치하는지 확인
        verify(authService).signup(signupRequest); // authService의 signup 메서드가 호출되었는지 검증
    }

    @Test
    public void testLogin() {
        // Given
        String username = "testuser";
        String password = "testpassword";
        LoginRequest loginRequest = new LoginRequest(username, password);

        // 로그인 응답을 Mocking
        String expectedToken = "testToken";  // "Bearer "는 LoginResponse에서 자르기 때문에 그냥 'testToken'만 설정
        LoginResponse loginResponse = new LoginResponse("Bearer " + expectedToken);

        // authService의 login 메서드가 호출되면 Mocking된 응답을 반환하도록 설정
        when(authService.login(loginRequest)).thenReturn(loginResponse);

        // When
        LoginResponse response = authController.login(loginRequest);

        // Then
        assertEquals(expectedToken, response.getToken());  // 반환된 토큰에서 "Bearer "가 잘라진 값이 'testToken'인지 확인
    }
}