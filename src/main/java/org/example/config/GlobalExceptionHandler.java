package org.example.config;

import org.example.common.dto.ApiResponse;
import org.example.common.exception.ApiException;
import org.example.common.enums.ErrorStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<String>> handleApiException(ApiException ex) {
        // 예외에서 ErrorStatus를 가져와 ReasonDto 생성
        ErrorStatus errorStatus = ex.getErrorCode();
        // ApiResponse에 ReasonDto를 넣어서 반환
        ApiResponse<String> apiResponse = ApiResponse.onFailure(errorStatus);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST); // 상태코드는 400 (BAD_REQUEST) 예시
    }
}
