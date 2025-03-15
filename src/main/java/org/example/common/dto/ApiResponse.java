package org.example.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.user.dto.response.UserResponseDto;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final T data;         // 성공 응답일 때의 데이터
    private final ErrorDetail error; // 실패 응답일 때의 에러 정보

    // 성공 응답 생성
    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(data, null);
    }

    // 실패 응답 생성 (data 없이 error만 포함, data: null 없이 error만 포함)
    public static ApiResponse<ErrorDetail> createError(ErrorDetail errorDetail) {
        return new ApiResponse<>(null, errorDetail); // data: null 없이 error만 포함
    }
}
