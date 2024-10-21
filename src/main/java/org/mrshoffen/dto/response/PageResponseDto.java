package org.mrshoffen.dto.response;

import java.util.List;

public record PageResponseDto(List<?> items,
                              long totalPages) {
}
