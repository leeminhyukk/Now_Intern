package org.example.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserAuthorityDto {

    private String username;
    private String nickname;
    private String role;
}
