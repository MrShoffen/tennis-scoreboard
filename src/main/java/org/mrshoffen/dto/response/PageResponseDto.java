package org.mrshoffen.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder
public class PageResponseDto {
    List<? extends EntityResponseDto> entities;
    Integer pageNumber;
    Integer pageSize;
    Integer totalPages;
}
