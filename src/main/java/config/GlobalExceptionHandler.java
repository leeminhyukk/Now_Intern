package config;

import common.dto.ReasonDto;
import common.exception.ApiException;
import jakarta.validation.constraints.Null;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Null>> handleApiException(ApiException ex) {
        ReasonDto status = ex.getErrorCode().getReasonHttpStatus();
        return getErrorResponse(status.getHttpStatus(), status.getMessage());
    }

    public ResponseEntity<ApiResponse<Null>> getErrorResponse(HttpStatus status, String message) {

        return new ResponseEntity<>(ApiResponse.createError(message, status.value()), status);
    }

}
