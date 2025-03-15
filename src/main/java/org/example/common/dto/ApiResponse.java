package org.example.common.dto;

import org.example.common.enums.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.user.dto.response.UserResponseDto;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private final T data;         // 성공 응답일 때의 데이터
    private final ReasonDto error; // 실패 응답일 때의 에러 정보

    // 성공 응답 생성
    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(data, null);
    }

    // 실패 응답 생성
    public static ApiResponse<String> createError(ReasonDto reasonDto) {
        return new ApiResponse<>(null, reasonDto);
    }

    // 실패 응답 기본 메서드
    public static ApiResponse<String> onFailure(ErrorStatus errorStatus) {
        ReasonDto reasonDto = errorStatus.getReason();
        return new ApiResponse<>(null, reasonDto);
    }
    // UserResponseDto 타입에 맞춰서 성공 응답을 반환하는 메서드 추가
    public static ApiResponse<UserResponseDto> createSuccessWithUserResponse(UserResponseDto userResponseDto) {
        return new ApiResponse<>(userResponseDto, null);
    }
}
