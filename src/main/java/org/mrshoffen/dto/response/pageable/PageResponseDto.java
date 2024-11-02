package org.mrshoffen.dto.response.pageable;

import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder
public class PageResponseDto {
    List<?> entities;
    Integer pageNumber;
    Integer pageSize;
    Integer totalPages;
}
