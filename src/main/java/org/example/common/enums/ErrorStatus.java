package org.example.common.enums;

import org.example.common.dto.ErrorDetail;
import org.example.common.dto.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    //jwt token 예외
    _BAD_REQUEST_UNSUPPORTED_TOKEN("BAD_REQUEST_UNSUPPORTED_TOKEN", "지원되지 않는 JWT 토큰입니다."),
    _BAD_REQUEST_ILLEGAL_TOKEN("BAD_REQUEST_ILLEGAL_TOKEN", "잘못된 JWT 토큰입니다."),
    _UNAUTHORIZED_INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 인증 토큰입니다."),
    _UNAUTHORIZED_EXPIRED_TOKEN("UNAUTHORIZED_EXPIRED_TOKEN", "만료된 JWT 토큰입니다."),
    _UNAUTHORIZED_TOKEN("UNAUTHORIZED_TOKEN", "JWT 토큰 검증 중 오류가 발생했습니다."),
    _FORBIDDEN_TOKEN("FORBIDDEN_TOKEN", "관리자 권한이 없습니다."),
    _NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "JWT 토큰이 필요합니다."),

    //Auth,USer관련 코드
    _USER_ALREADY_EXISTS("USER_ALREADY_EXISTS","이미 가입된 사용자입니다."),
    _USERNAME_IS_SAME("USERNAME_IS_SAME", "변경하려는 이름이 전과 동일합니다"),
    _DELETED_USER("DELETED_USER", "탈퇴한 계정입니다."),
    _PASSWORD_NOT_MATCHES("INVALID_CREDENTIALS", "비밀번호가 올바르지 않습니다."),
    _INVALID_PASSWORD_FORM("INVALID_PASSWORD_FORM", "비밀번호는 최소 8자 이상이어야 하며, 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."),
    _INVALID_USER_INFO("INVALID_USER_INFO", "변경하려는 정보가 잘못되었습니다."),
    _INVALID_BIRTHDAY("INVALID_BIRTHDAY", "잘못된 생일 값입니다"),
    _BAD_REQUEST_NOT_FOUND_USER("INVALID_CREDENTIALS", "아이디가 올바르지 않습니다."),
    _PASSWORD_IS_DUPLICATED("PASSWORD_IS_DUPLICATED", "이미 사용중인 비밀번호로 변경할 수 없습니다."),
    _INVALID_USER_ROLE("INVALID_USER_ROLE", "잘못된 유저권한 입니다."),
    _USER_ROLE_IS_NULL("USER_ROLE_IS_NULL", "유저 권한이 없습니다."),
    _INVALID_USER_NAME("INVALID_USER_NAME", "유저이름은 최소 3자 이상,20자 이하여야 하며, 대소문자 포함 영문,숫자만 사용가능합니다."),
    _USER_NOT_FOUND("USER_NOT_FOUND", "해당 유저를 찾을 수 없습니다."),

    // 서버 예외
    _INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다."),
    _NOT_FOUND_ROLE("NOT_FOUND_ROLE", "해당 권한이 없습니다"),
    _NOT_PERMITTED_USER("ACCESS_DENIED", "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."),
    _BAD_REQUEST("BAD_REQUEST", "잘못된 입력입니다.");

    private final String errorCode; // 영어로 된 에러 코드
    private final String message; // 한글로 된 메시지

    // ErrorStatus 객체에서 ReasonDto를 반환하는 메서드 (응답 형식에 맞게)
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .error(new ErrorDetail(errorCode, message)) // errorCode와 message를 직접 설정
                .build();
    }
}
