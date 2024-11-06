package org.mrshoffen.dto.request;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class PageRequestDto {
    Integer pageNumber;
    Integer pageSize;
    String playerName;
}
