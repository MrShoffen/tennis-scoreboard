package org.mrshoffen.dto.response;

import lombok.Value;

@Value(staticConstructor = "of")
public class ErrorResponseDto {
    private String message;
}