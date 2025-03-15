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
    public ResponseEntity<ApiResponse<ErrorDetail>> handleApiException(ApiException ex) {
        // ApiException에서 errorCode를 가져와 ErrorDetail로 변환
        ErrorDetail errorDetail = new ErrorDetail(ex.getErrorCode().getErrorCode(), ex.getErrorCode().getMessage());

        // ApiResponse를 error 정보만 포함하도록 설정
        ApiResponse<ErrorDetail> apiResponse = ApiResponse.createError(errorDetail);

        // BAD_REQUEST 상태 코드로 응답 반환
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
