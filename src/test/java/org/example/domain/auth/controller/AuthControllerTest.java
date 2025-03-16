package org.example.domain.auth.controller;

import org.example.domain.auth.dto.request.SignupRequest;
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
}