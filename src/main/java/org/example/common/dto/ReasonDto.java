package org.example.common.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReasonDto {
    private ErrorDetail error;
}