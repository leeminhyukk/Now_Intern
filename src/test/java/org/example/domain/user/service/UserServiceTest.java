package org.example.domain.user.service;

import org.example.common.enums.ErrorStatus;
import org.example.common.exception.ApiException;
import org.example.config.AuthUser;
import org.example.domain.user.dto.response.UpdateUserAuthorityDto;
import org.example.domain.user.entity.User;
import org.example.domain.user.enums.UserRole;
import org.example.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthUser authUser;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("testUsername", "testNickname", "encodedPassword", UserRole.USER);
        // user 객체는 저장되기 전까지 userId가 null이므로 save() 메서드를 사용해 저장 후 자동으로 id가 할당될 것임

    }

    @Test
    void updateUserAuthority_AdminRole_ChangeToAdmin() {
        // given
        when(authUser.getUserRole()).thenReturn(UserRole.ADMIN);
        when(userRepository.findById(user.getUserId())).thenReturn(java.util.Optional.of(user));

        // when
        UpdateUserAuthorityDto result = userService.updateUserAuthority(user.getUserId(), authUser);

        // then
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getRole()).isEqualTo("ADMIN");
        assertThat(user.getUserRole()).isEqualTo(UserRole.ADMIN);

        // 수정된 부분: save 호출을 정확히 한 번만 확인
        verify(userRepository, times(1)).save(user); // Ensure save is called only once
    }

    @Test
    void updateUserAuthority_UserRole_FailDueToPermission() {
        // given
        when(authUser.getUserRole()).thenReturn(UserRole.USER); // Trying with USER role
        when(userRepository.findById(user.getUserId())).thenReturn(java.util.Optional.of(user));

        // when & then
        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.updateUserAuthority(user.getUserId(), authUser);
        });

        // 수정된 부분: getErrorStatus -> getErrorCode로 변경
        assertThat(exception.getErrorCode()).isEqualTo(ErrorStatus._NOT_PERMITTED_USER);
        verify(userRepository, never()).save(any()); // Ensure save is not called
    }

}
