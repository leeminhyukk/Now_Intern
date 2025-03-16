package org.example.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class updateUserAuthorityRequestDto {
    @Schema(description = "권한", example = "ADMIN")
    private String UserRole;
}
