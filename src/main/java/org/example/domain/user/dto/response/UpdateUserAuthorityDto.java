package org.example.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserAuthorityDto {

    @Schema(description = "사용자 이름", example = "JIN HO")
    private String username;
    @Schema(description = "별명", example = "JINHOKING")
    private String nickname;
    @Schema(description = "권한", example = "ADMIN")
    private String role;
}
