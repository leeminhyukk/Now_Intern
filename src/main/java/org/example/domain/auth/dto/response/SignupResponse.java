package org.example.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {

    @Schema(description = "사용자 이름", example = "JIN HO")
    private String username;
    @Schema(description = "별명", example = "JINHOKING")
    private String nickname;
    @Schema(description = "권한", example = "ADMIN")
    private String role;
}
