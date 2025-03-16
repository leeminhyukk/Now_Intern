package org.example.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.example.common.dto.ApiResponse;
import org.example.common.dto.ErrorDetail;
import org.example.common.exception.ApiException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex) {
        // ApiException에서 errorCode를 가져와 ErrorDetail로 변환
        ErrorDetail errorDetail = new ErrorDetail(
                ex.getErrorCode().getErrorCode(),
                ex.getErrorCode().getMessage()
        );

        // 최종 응답 객체
        ApiResponse<ErrorDetail> apiResponse = ApiResponse.createError(errorDetail);

        // JSON 형태가 정확한지 확인하고, 상태 코드만 400으로 설정
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
