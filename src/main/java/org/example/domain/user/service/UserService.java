package org.example.domain.user.service;

import org.example.common.enums.ErrorStatus;
import org.example.common.exception.ApiException;
import org.example.config.AuthUser;
import org.example.domain.user.dto.request.DeleteUserRequestDto;
import org.example.domain.user.dto.response.UpdateUserAuthorityDto;
import org.example.domain.user.entity.User;
import org.example.domain.user.enums.UserRole;
import org.example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원 탈퇴
    @Transactional
    public void deletedUser(AuthUser authUser, DeleteUserRequestDto deleteUserRequestDto) {
        // authUser 닉네임으로 현재 로그인한 User 찾기
        User user = userRepository.findByUsername(authUser.getNickname())
                .orElseThrow(()-> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));

        // 회원의 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(deleteUserRequestDto.getPassword(), user.getPassword())){
            throw new ApiException(ErrorStatus._PASSWORD_NOT_MATCHES);
        }

        // 이미 탈퇴한 회원인지 확인
        if (user.getIsdeleted()){
            throw new ApiException(ErrorStatus._DELETED_USER);
        }

        // 회원 탈퇴 메소드
        user.deletedUser(user.getNickname(), user.getPassword());
        // 변경된 내용 저장
        userRepository.save(user);
    }

    @Transactional
    public UpdateUserAuthorityDto updateUserAuthority(Long userId, AuthUser authUser) {
        if(!authUser.getUserRole().equals(UserRole.ADMIN)) {
            throw new ApiException(ErrorStatus._NOT_PERMITTED_USER);
        }
        User user = userRepository.findById(userId).orElseThrow(()-> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));

        if(user.getIsdeleted()){
            throw new ApiException(ErrorStatus._DELETED_USER);
        }
        user.updateUserRole(UserRole.ADMIN);

        // 변경된 내용을 저장
        userRepository.save(user);

        return new UpdateUserAuthorityDto(user.getUsername(),user.getNickname(),user.getUserRole().toString());
    }

}
