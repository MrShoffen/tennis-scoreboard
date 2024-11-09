package org.mrshoffen.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class PageRequestDto {

    @Positive(message = "Page number must be positive!")
    Integer pageNumber;

    @Positive(message = "Page size must be positive!")
    Integer pageSize;

    String playerNameFilterBy;
}
