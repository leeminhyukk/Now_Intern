package org.example.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DeleteUserRequestDto {
    @Schema(description = "비밀번호",example = "123456~!")
    private String password;
}
