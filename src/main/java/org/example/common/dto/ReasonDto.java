package org.example.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReasonDto {
    private ErrorDetail error;
}
